package com.boaz.lp.admin.service

import com.boaz.lp.admin.config.BatchServerProperties
import com.boaz.lp.storage.entity.SystemConfigEntity
import com.boaz.lp.storage.repository.SystemConfigRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class RiotApiKeyService(
    private val systemConfigRepository: SystemConfigRepository,
    private val batchServerProperties: BatchServerProperties
) {
    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        const val CONFIG_KEY_RIOT_API_KEY = "RIOT_API_KEY"
    }

    private val batchWebClient: WebClient = WebClient.builder()
        .baseUrl(batchServerProperties.url)
        .build()

    fun getCurrentKeyMasked(): String? {
        val config = systemConfigRepository.findByConfigKey(CONFIG_KEY_RIOT_API_KEY) ?: return null
        val key = config.configValue
        if (key.length <= 8) return "****"
        return "${key.take(4)}...${key.takeLast(4)}"
    }

    fun updateApiKey(newKey: String) {
        // 1. Save to DB
        val config = systemConfigRepository.findByConfigKey(CONFIG_KEY_RIOT_API_KEY)
        if (config != null) {
            config.configValue = newKey
            systemConfigRepository.save(config)
        } else {
            systemConfigRepository.save(
                SystemConfigEntity(
                    configKey = CONFIG_KEY_RIOT_API_KEY,
                    configValue = newKey,
                    description = "Riot Games API Key"
                )
            )
        }
        log.info("Riot API key saved to database")

        // 2. Notify batch server
        try {
            batchWebClient.post()
                .uri("/internal/api-key")
                .header("X-Internal-Key", batchServerProperties.internalKey)
                .bodyValue(mapOf("apiKey" to newKey))
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
            log.info("Batch server notified of API key update")
        } catch (e: Exception) {
            log.warn("Failed to notify batch server of API key update: ${e.message}")
            throw RuntimeException("API key saved to DB, but failed to notify batch server: ${e.message}")
        }
    }
}
