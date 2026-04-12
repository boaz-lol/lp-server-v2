package com.boaz.lp.admin.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "batch.server")
data class BatchServerProperties(
    val url: String = "http://localhost:8082",
    val internalKey: String = "batch-internal-secret-key"
)
