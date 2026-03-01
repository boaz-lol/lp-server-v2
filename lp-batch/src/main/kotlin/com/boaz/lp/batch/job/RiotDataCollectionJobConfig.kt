package com.boaz.lp.batch.job

import org.springframework.batch.core.job.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.Step
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class RiotDataCollectionJobConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val summonerCollectionTasklet: SummonerCollectionTasklet,
    private val matchCollectionTasklet: MatchCollectionTasklet,
    private val rankCollectionTasklet: RankCollectionTasklet
) {

    @Bean
    fun riotDataCollectionJob(): Job {
        return JobBuilder("riotDataCollectionJob", jobRepository)
            .start(collectSummonerStep())
            .next(collectMatchesStep())
            .next(collectRanksStep())
            .build()
    }

    @Bean
    fun collectSummonerStep(): Step {
        return StepBuilder("collectSummonerStep", jobRepository)
            .tasklet(summonerCollectionTasklet, transactionManager)
            .build()
    }

    @Bean
    fun collectMatchesStep(): Step {
        return StepBuilder("collectMatchesStep", jobRepository)
            .tasklet(matchCollectionTasklet, transactionManager)
            .build()
    }

    @Bean
    fun collectRanksStep(): Step {
        return StepBuilder("collectRanksStep", jobRepository)
            .tasklet(rankCollectionTasklet, transactionManager)
            .build()
    }
}
