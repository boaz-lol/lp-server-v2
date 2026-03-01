package com.boaz.lp.batch

import com.boaz.lp.batch.config.RiotApiProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(scanBasePackages = ["com.boaz.lp"])
@EnableScheduling
@EnableConfigurationProperties(RiotApiProperties::class)
@EntityScan(basePackages = ["com.boaz.lp.storage.entity"])
class BatchServerApplication

fun main(args: Array<String>) {
    runApplication<BatchServerApplication>(*args)
}
