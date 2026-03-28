package com.boaz.lp.batch.client

import com.boaz.lp.batch.client.dto.*
import com.boaz.lp.batch.config.RiotApiProperties
import com.boaz.lp.common.constants.Constants
import com.boaz.lp.common.enum.RiotRegion
import com.boaz.lp.common.exception.RiotApiException
import com.boaz.lp.common.exception.RiotApiRateLimitException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class RiotApiClient(
    private val riotApiWebClient: WebClient,
    private val rateLimiter: RiotApiRateLimiter,
    private val properties: RiotApiProperties
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun getAccountByRiotId(gameName: String, tagLine: String): RiotAccountDto {
        rateLimiter.acquire()
        val url = "${Constants.RiotApi.BASE_URL_ASIA}/riot/account/v1/accounts/by-riot-id/$gameName/$tagLine"
        return executeGet(url, RiotAccountDto::class.java)
    }

    fun getSummonerByPuuid(puuid: String, region: RiotRegion): RiotSummonerDto {
        rateLimiter.acquire()
        val baseUrl = String.format(Constants.RiotApi.PLATFORM_URL_FORMAT, region.platformId.lowercase())
        val url = "$baseUrl/lol/summoner/v4/summoners/by-puuid/$puuid"

        // Fetch raw response first to diagnose missing 'id' field
        val rawBody = riotApiWebClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
        log.debug("Summoner API raw response: {}", rawBody)

        val mapper = com.fasterxml.jackson.module.kotlin.jacksonObjectMapper()
        val result = mapper.readValue(rawBody, RiotSummonerDto::class.java)
        if (result.id == null) {
            log.warn("Summoner API returned null 'id' for puuid={}. Raw: {}. Rank collection will be skipped.", puuid, rawBody)
        }
        return result
    }

    fun getMatchIds(puuid: String, start: Int = 0, count: Int = 20, startTime: Long? = null): List<String> {
        rateLimiter.acquire()
        val url = buildString {
            append("${Constants.RiotApi.BASE_URL_ASIA}/lol/match/v5/matches/by-puuid/$puuid/ids")
            append("?start=$start&count=$count")
            if (startTime != null) {
                append("&startTime=$startTime")
            }
        }
        return executeGet(url, Array<String>::class.java).toList()
    }

    fun getMatchDetail(matchId: String): RiotMatchDto {
        rateLimiter.acquire()
        val url = "${Constants.RiotApi.BASE_URL_ASIA}/lol/match/v5/matches/$matchId"
        return executeGet(url, RiotMatchDto::class.java)
    }

    fun getLeagueEntries(summonerId: String, region: RiotRegion): List<RiotLeagueEntryDto> {
        rateLimiter.acquire()
        val baseUrl = String.format(Constants.RiotApi.PLATFORM_URL_FORMAT, region.platformId.lowercase())
        val url = "$baseUrl/lol/league/v4/entries/by-summoner/$summonerId"
        return executeGet(url, Array<RiotLeagueEntryDto>::class.java).toList()
    }

    private fun <T : Any> executeGet(url: String, responseType: Class<T>): T {
        var lastException: Throwable? = null
        repeat(properties.retryMaxAttempts) { attempt ->
            try {
                return riotApiWebClient.get()
                    .uri(url)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError) { response ->
                        if (response.statusCode().value() == 429) {
                            val retryAfter = response.headers().asHttpHeaders()
                                .getFirst(Constants.RiotApi.HEADER_RETRY_AFTER)?.toLongOrNull() ?: 1
                            log.warn("Rate limited by Riot API. Retry-After: ${retryAfter}s")
                            Mono.error(RiotApiRateLimitException("Rate limited. Retry after ${retryAfter}s"))
                        } else {
                            response.bodyToMono(String::class.java)
                                .defaultIfEmpty("No response body")
                                .flatMap { body ->
                                    Mono.error(RiotApiException("Riot API ${response.statusCode().value()}: $body"))
                                }
                        }
                    }
                    .onStatus(HttpStatusCode::is5xxServerError) { response ->
                        response.bodyToMono(String::class.java)
                            .defaultIfEmpty("No response body")
                            .flatMap { body ->
                                Mono.error(RiotApiException("Riot API server error ${response.statusCode().value()}: $body"))
                            }
                    }
                    .bodyToMono(responseType)
                    .block()!!
            } catch (e: RiotApiRateLimitException) {
                lastException = e
                log.info("Rate limited, backing off (attempt ${attempt + 1}/${properties.retryMaxAttempts})")
                Thread.sleep((attempt + 1) * 1000L)
            } catch (e: RiotApiException) {
                throw e
            } catch (e: Exception) {
                lastException = e
                log.warn("API call failed (attempt ${attempt + 1}/${properties.retryMaxAttempts}): ${e.message}")
                if (attempt < properties.retryMaxAttempts - 1) {
                    Thread.sleep((attempt + 1) * 500L)
                }
            }
        }
        throw lastException as? RiotApiException
            ?: RiotApiException("Failed after ${properties.retryMaxAttempts} attempts", lastException)
    }
}
