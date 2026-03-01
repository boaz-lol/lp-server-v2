package com.boaz.lp.batch.job

import com.boaz.lp.batch.client.RiotApiClient
import com.boaz.lp.batch.mapper.RiotDataMapper
import com.boaz.lp.common.enum.RiotRegion
import com.boaz.lp.storage.repository.SummonerRepository
import org.slf4j.LoggerFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.StepContribution
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.infrastructure.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class SummonerCollectionTasklet(
    private val riotApiClient: RiotApiClient,
    private val summonerRepository: SummonerRepository,
    private val mapper: RiotDataMapper
) : Tasklet {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val jobParameters = chunkContext.stepContext.jobParameters

        val gameName = jobParameters["gameName"] as? String
        val tagLine = jobParameters["tagLine"] as? String
        val regionStr = jobParameters["region"] as? String

        if (gameName != null && tagLine != null && regionStr != null) {
            val region = RiotRegion.valueOf(regionStr)
            log.info("Collecting seed summoner: $gameName#$tagLine ($region)")
            collectSummoner(gameName, tagLine, region)
        } else {
            log.info("No seed summoner specified. Refreshing all existing summoners.")
            val summoners = summonerRepository.findAll()
            summoners.forEach { summoner ->
                try {
                    val account = riotApiClient.getAccountByRiotId(summoner.gameName, summoner.tagLine)
                    val summonerDto = riotApiClient.getSummonerByPuuid(account.puuid, summoner.region)
                    mapper.updateSummonerEntity(summoner, account, summonerDto)
                    summonerRepository.save(summoner)
                    log.info("Refreshed summoner: ${summoner.gameName}#${summoner.tagLine}")
                } catch (e: Exception) {
                    log.error("Failed to refresh summoner ${summoner.gameName}#${summoner.tagLine}: ${e.message}")
                }
            }
        }

        return RepeatStatus.FINISHED
    }

    private fun collectSummoner(gameName: String, tagLine: String, region: RiotRegion) {
        val account = riotApiClient.getAccountByRiotId(gameName, tagLine)
        val summonerDto = riotApiClient.getSummonerByPuuid(account.puuid, region)

        val existing = summonerRepository.findByPuuid(account.puuid)
        if (existing != null) {
            mapper.updateSummonerEntity(existing, account, summonerDto)
            summonerRepository.save(existing)
            log.info("Updated existing summoner: $gameName#$tagLine")
        } else {
            val entity = mapper.toSummonerEntity(account, summonerDto, region)
            summonerRepository.save(entity)
            log.info("Saved new summoner: $gameName#$tagLine")
        }
    }
}
