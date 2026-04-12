package com.boaz.lp.storage.entity

import com.boaz.lp.storage.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "system_config")
class SystemConfigEntity(

    @Column(name = "config_key", nullable = false, unique = true)
    var configKey: String,

    @Column(name = "config_value", nullable = false, length = 1000)
    var configValue: String,

    @Column(name = "description")
    var description: String? = null

) : BaseEntity()
