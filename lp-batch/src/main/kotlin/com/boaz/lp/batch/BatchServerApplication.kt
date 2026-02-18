package com.boaz.lp.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * Batch Server Application
 *
 * Spring Batch jobs for data collection and processing
 * - Riot API data collection
 * - Python ML model inference
 * - Scheduled batch jobs
 *
 * Note: @EnableBatchProcessing and JPA configuration will be added in Phase 2
 */
@SpringBootApplication(scanBasePackages = ["com.boaz.lp"])
@EnableScheduling
class BatchServerApplication

fun main(args: Array<String>) {
    runApplication<BatchServerApplication>(*args)
}
