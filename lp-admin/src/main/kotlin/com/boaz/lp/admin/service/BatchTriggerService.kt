package com.boaz.lp.admin.service

import com.boaz.lp.admin.config.BatchServerProperties
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class BatchTriggerService(
    private val batchServerProperties: BatchServerProperties
) {
    private val log = LoggerFactory.getLogger(javaClass)

    private val batchWebClient: WebClient = WebClient.builder()
        .baseUrl(batchServerProperties.url)
        .build()

    fun triggerJob(): String {
        try {
            val response = batchWebClient.post()
                .uri("/internal/trigger")
                .header("X-Internal-Key", batchServerProperties.internalKey)
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
            log.info("Batch job trigger response: {}", response)
            return "배치 작업이 실행되었습니다."
        } catch (e: Exception) {
            log.error("Failed to trigger batch job: {}", e.message, e)
            throw RuntimeException("배치 서버에 연결할 수 없습니다: ${e.message}")
        }
    }
}
