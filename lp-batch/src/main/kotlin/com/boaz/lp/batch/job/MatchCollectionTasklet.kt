package com.boaz.lp.batch.job

import com.boaz.lp.batch.client.RiotApiClient
import com.boaz.lp.batch.mapper.RiotDataMapper
import com.boaz.lp.storage.repository.MatchRepository
import com.boaz.lp.storage.repository.SummonerRepository
import org.slf4j.LoggerFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.StepContribution
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.infrastructure.repeat.RepeatStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset

@Component
class MatchCollectionTasklet(
    private val riotApiClient: RiotApiClient,
    private val summonerRepository: SummonerRepository,
    private val matchRepository: MatchRepository,
    private val mapper: RiotDataMapper
) : Tasklet {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val summoners = summonerRepository.findAll()
        log.info("Collecting matches for ${summoners.size} summoners")

        summoners.forEach { summoner ->
            try {
                val startTime = summoner.lastMatchCollectedAt
                    ?.toEpochSecond(ZoneOffset.UTC)

                val matchIds = riotApiClient.getMatchIds(
                    puuid = summoner.puuid,
                    start = 0,
                    count = 20,
                    startTime = startTime
                )

                var collected = 0
                matchIds.forEach { matchId ->
                    if (!matchRepository.existsByMatchId(matchId)) {
                        try {
                            val matchDto = riotApiClient.getMatchDetail(matchId)
                            val matchEntity = mapper.toMatchEntity(matchDto)
                            matchRepository.save(matchEntity)
                            collected++
                        } catch (e: Exception) {
                            log.error("Failed to collect match $matchId: ${e.message}")
                        }
                    }
                }

                summoner.lastMatchCollectedAt = LocalDateTime.now()
                summonerRepository.save(summoner)
                log.info("Collected $collected new matches for ${summoner.gameName}#${summoner.tagLine}")
            } catch (e: Exception) {
                log.error("Failed to collect matches for ${summoner.gameName}#${summoner.tagLine}: ${e.message}")
            }
        }

        return RepeatStatus.FINISHED
    }
}
