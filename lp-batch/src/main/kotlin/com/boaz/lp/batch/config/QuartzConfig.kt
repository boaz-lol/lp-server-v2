package com.boaz.lp.batch.config

import com.boaz.lp.batch.scheduler.RiotDataCollectionQuartzJob
import org.quartz.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuartzConfig {

    @Bean
    fun riotDataCollectionJobDetail(): JobDetail {
        return JobBuilder.newJob(RiotDataCollectionQuartzJob::class.java)
            .withIdentity("riotDataCollectionJob", "batch")
            .storeDurably()
            .build()
    }

    @Bean
    fun riotDataCollectionTrigger(riotDataCollectionJobDetail: JobDetail): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(riotDataCollectionJobDetail)
            .withIdentity("riotDataCollectionTrigger", "batch")
            .withSchedule(
                CronScheduleBuilder.cronSchedule("0 */2 * * * ?") // Every 2 minutes
                    .withMisfireHandlingInstructionDoNothing()
            )
            .build()
    }
}
