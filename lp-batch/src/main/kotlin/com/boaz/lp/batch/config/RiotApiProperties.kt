package com.boaz.lp.batch.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "riot.api")
data class RiotApiProperties(
    val key: String = "",
    val rateLimitPerSecond: Int = 20,
    val rateLimitPer2Minutes: Int = 100,
    val retryMaxAttempts: Int = 3
)
