package com.boaz.lp.batch.job

import com.boaz.lp.batch.client.RiotApiClient
import com.boaz.lp.batch.mapper.RiotDataMapper
import com.boaz.lp.storage.repository.RankInfoRepository
import com.boaz.lp.storage.repository.SummonerRepository
import org.slf4j.LoggerFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.StepContribution
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.infrastructure.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class RankCollectionTasklet(
    private val riotApiClient: RiotApiClient,
    private val summonerRepository: SummonerRepository,
    private val rankInfoRepository: RankInfoRepository,
    private val mapper: RiotDataMapper
) : Tasklet {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val summoners = summonerRepository.findAll()
        log.info("Collecting rank info for ${summoners.size} summoners")

        summoners.forEach { summoner ->
            try {
                val entries = riotApiClient.getLeagueEntries(summoner.summonerId, summoner.region)

                entries.forEach { entry ->
                    val existing = rankInfoRepository.findBySummonerIdAndQueueType(
                        summoner.id!!, entry.queueType
                    )

                    if (existing != null) {
                        mapper.updateRankInfoEntity(existing, entry)
                        rankInfoRepository.save(existing)
                    } else {
                        val entity = mapper.toRankInfoEntity(entry, summoner)
                        rankInfoRepository.save(entity)
                    }
                }

                log.info("Updated rank info for ${summoner.gameName}#${summoner.tagLine}: ${entries.size} queues")
            } catch (e: Exception) {
                log.error("Failed to collect rank for ${summoner.gameName}#${summoner.tagLine}: ${e.message}")
            }
        }

        return RepeatStatus.FINISHED
    }
}
