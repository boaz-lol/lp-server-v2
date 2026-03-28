package com.boaz.lp.batch.config

import org.springframework.boot.quartz.autoconfigure.SchedulerFactoryBeanCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuartzJobFactoryConfig {

    @Bean
    fun schedulerFactoryBeanCustomizer(): SchedulerFactoryBeanCustomizer {
        return SchedulerFactoryBeanCustomizer { bean ->
            bean.setWaitForJobsToCompleteOnShutdown(true)
            bean.setOverwriteExistingJobs(true)
        }
    }
}
