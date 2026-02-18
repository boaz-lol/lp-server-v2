package com.boaz.lp.admin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Admin Server Application
 *
 * Admin backoffice for system management
 * Port: 8081
 * - Data management
 * - Job management
 * - System monitoring
 *
 * Note: JPA configuration will be added in Phase 2
 */
@SpringBootApplication(scanBasePackages = ["com.boaz.lp"])
class AdminServerApplication

fun main(args: Array<String>) {
    runApplication<AdminServerApplication>(*args)
}
