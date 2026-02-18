package com.boaz.lp.storage.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

/**
 * DataSource configuration based on active profile
 *
 * - local: SQLite (file-based, no installation needed)
 * - prod: MySQL (remote database)
 */
@Configuration
class DataSourceConfig {

    /**
     * SQLite DataSource for local development
     *
     * No separate database installation required
     * Database file will be created at ./data/lol-match.db
     */
    @Bean
    @Profile("local")
    fun localDataSource(): DataSource {
        return DriverManagerDataSource().apply {
            setDriverClassName("org.sqlite.JDBC")
            url = "jdbc:sqlite:./data/lol-match.db"
            // SQLite doesn't require username/password
        }
    }

    /**
     * MySQL DataSource for production
     *
     * Uses environment variables for configuration:
     * - DB_HOST: Database host (default: mysql)
     * - DB_PORT: Database port (default: 3306)
     * - DB_NAME: Database name (default: lol_match)
     * - DB_USER: Database username
     * - DB_PASSWORD: Database password
     */
    @Bean
    @Profile("prod")
    fun prodDataSource(
        @Value("\${spring.datasource.url}") url: String,
        @Value("\${spring.datasource.username}") username: String,
        @Value("\${spring.datasource.password}") password: String
    ): DataSource {
        return DriverManagerDataSource().apply {
            setDriverClassName("com.mysql.cj.jdbc.Driver")
            this.url = url
            this.username = username
            this.password = password
        }
    }
}
