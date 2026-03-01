package com.boaz.lp.batch.job

import com.boaz.lp.batch.client.RiotApiClient
import com.boaz.lp.batch.mapper.RiotDataMapper
import com.boaz.lp.common.enum.RiotRegion
import com.boaz.lp.storage.repository.SummonerRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class SeedSummonerRunner(
    private val riotApiClient: RiotApiClient,
    private val summonerRepository: SummonerRepository,
    private val mapper: RiotDataMapper
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    data class SeedSummoner(val gameName: String, val tagLine: String, val region: RiotRegion)

    private val seeds = listOf(
        SeedSummoner("Hide on bush", "KR1", RiotRegion.KR)
    )

    override fun run(args: ApplicationArguments) {
        seeds.forEach { seed ->
            try {
                val existing = summonerRepository.findByGameNameAndTagLineAndRegion(
                    seed.gameName, seed.tagLine, seed.region
                )
                if (existing != null) {
                    log.info("Seed summoner already exists: ${seed.gameName}#${seed.tagLine}")
                    return@forEach
                }

                val account = riotApiClient.getAccountByRiotId(seed.gameName, seed.tagLine)
                val summoner = riotApiClient.getSummonerByPuuid(account.puuid, seed.region)
                val entity = mapper.toSummonerEntity(account, summoner, seed.region)
                summonerRepository.save(entity)
                log.info("Registered seed summoner: ${seed.gameName}#${seed.tagLine}")
            } catch (e: Exception) {
                log.error("Failed to register seed summoner ${seed.gameName}#${seed.tagLine}: ${e.message}")
            }
        }
    }
}
