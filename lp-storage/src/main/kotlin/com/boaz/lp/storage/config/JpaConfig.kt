package com.boaz.lp.storage.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

/**
 * JPA Configuration
 *
 * Enables JPA Auditing for @CreatedDate and @LastModifiedDate annotations
 * in BaseEntity
 */
@Configuration
@EnableJpaAuditing
class JpaConfig
