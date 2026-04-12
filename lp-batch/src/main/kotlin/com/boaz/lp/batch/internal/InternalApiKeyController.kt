package com.boaz.lp.batch.internal

import com.boaz.lp.batch.config.ApiKeyHolder
import org.quartz.Scheduler
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class ApiKeyUpdateRequest(val apiKey: String)

@RestController
@RequestMapping("/internal")
class InternalApiKeyController(
    private val apiKeyHolder: ApiKeyHolder,
    private val scheduler: Scheduler,
    @Value("\${batch.internal-key}") private val internalKey: String
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/api-key")
    fun updateApiKey(
        @RequestHeader("X-Internal-Key") requestKey: String,
        @RequestBody request: ApiKeyUpdateRequest
    ): ResponseEntity<Map<String, String>> {
        if (requestKey != internalKey) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(mapOf("error" to "Invalid internal key"))
        }

        try {
            log.info("Pausing scheduler for API key update")
            scheduler.pauseAll()

            apiKeyHolder.updateKey(request.apiKey)

            log.info("Scheduler resumed after API key update")
            scheduler.resumeAll()

            return ResponseEntity.ok(mapOf("status" to "API key updated successfully"))
        } catch (e: Exception) {
            log.error("Error updating API key", e)
            try {
                scheduler.resumeAll()
                log.info("Scheduler resumed after error")
            } catch (re: Exception) {
                log.error("Failed to resume scheduler after error", re)
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "Failed to update API key: ${e.message}"))
        }
    }
}
