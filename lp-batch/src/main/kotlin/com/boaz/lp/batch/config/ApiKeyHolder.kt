package com.boaz.lp.batch.config

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicReference

@Component
class ApiKeyHolder(riotApiProperties: RiotApiProperties) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val currentKey = AtomicReference(riotApiProperties.key)

    fun getKey(): String = currentKey.get()

    fun updateKey(newKey: String) {
        val oldKeyPrefix = currentKey.get().take(4)
        currentKey.set(newKey)
        log.info("API key updated (old prefix: {}... → new prefix: {}...)", oldKeyPrefix, newKey.take(4))
    }
}
