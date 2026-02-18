package com.boaz.lp.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * API Server Application
 *
 * REST API backend for frontend
 * Port: 8080
 *
 * Note: JPA configuration will be added in Phase 2
 */
@SpringBootApplication(scanBasePackages = ["com.boaz.lp"])
class ApiServerApplication

fun main(args: Array<String>) {
    runApplication<ApiServerApplication>(*args)
}
