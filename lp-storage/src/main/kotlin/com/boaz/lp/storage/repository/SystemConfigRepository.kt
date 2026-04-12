package com.boaz.lp.storage.repository

import com.boaz.lp.storage.entity.SystemConfigEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SystemConfigRepository : JpaRepository<SystemConfigEntity, Long> {
    fun findByConfigKey(configKey: String): SystemConfigEntity?
}
