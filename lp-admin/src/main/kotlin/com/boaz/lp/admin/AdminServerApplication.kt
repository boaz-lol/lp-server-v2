package com.boaz.lp.admin

import com.boaz.lp.admin.config.BatchServerProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.boaz.lp"])
@EnableConfigurationProperties(BatchServerProperties::class)
@EntityScan(basePackages = ["com.boaz.lp.storage.entity"])
class AdminServerApplication

fun main(args: Array<String>) {
    runApplication<AdminServerApplication>(*args)
}
