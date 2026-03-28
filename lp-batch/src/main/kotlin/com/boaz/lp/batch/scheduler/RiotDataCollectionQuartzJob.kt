package com.boaz.lp.batch.scheduler

import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.batch.core.job.Job
import org.springframework.batch.core.job.parameters.JobParametersBuilder
import org.springframework.batch.core.launch.JobOperator
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component

@Component
class RiotDataCollectionQuartzJob(
    private val jobOperator: JobOperator,
    private val riotDataCollectionJob: Job
) : QuartzJobBean() {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun executeInternal(context: JobExecutionContext) {
        log.info("Quartz triggered: riotDataCollectionJob")
        try {
            val params = JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters()
            val execution = jobOperator.start(riotDataCollectionJob, params)
            log.info("Job completed with status: ${execution.status}")
        } catch (e: Exception) {
            log.error("Failed to execute riotDataCollectionJob: ${e.message}", e)
        }
    }
}
