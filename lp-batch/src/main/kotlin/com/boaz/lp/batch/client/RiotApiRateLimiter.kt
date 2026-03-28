package com.boaz.lp.batch.client

import com.boaz.lp.batch.config.RiotApiProperties
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

@Component
class RiotApiRateLimiter(
    private val properties: RiotApiProperties
) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val semaphore = Semaphore(properties.rateLimitPerSecond)

    init {
        Thread.ofVirtual().name("rate-limiter-refill").start {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    TimeUnit.SECONDS.sleep(1)
                    val permitsToRelease = properties.rateLimitPerSecond - semaphore.availablePermits()
                    if (permitsToRelease > 0) {
                        semaphore.release(permitsToRelease)
                    }
                } catch (_: InterruptedException) {
                    Thread.currentThread().interrupt()
                    break
                }
            }
        }
    }

    fun acquire() {
        if (!semaphore.tryAcquire(10, TimeUnit.SECONDS)) {
            log.warn("Rate limiter timeout - could not acquire permit within 10s")
            throw com.boaz.lp.common.exception.RiotApiRateLimitException("Rate limiter timeout")
        }
    }
}
