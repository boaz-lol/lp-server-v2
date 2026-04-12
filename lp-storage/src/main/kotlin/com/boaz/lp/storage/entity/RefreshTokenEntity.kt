package com.boaz.lp.storage.entity

import com.boaz.lp.storage.entity.base.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "refresh_token",
    indexes = [
        Index(name = "idx_refresh_token_member_id", columnList = "member_id"),
        Index(name = "idx_refresh_token_token", columnList = "token")
    ]
)
class RefreshTokenEntity(

    @Column(name = "member_id", nullable = false)
    var memberId: Long,

    @Column(name = "token", nullable = false, unique = true, length = 512)
    var token: String,

    @Column(name = "expires_at", nullable = false)
    var expiresAt: LocalDateTime

) : BaseEntity() {

    fun isExpired(): Boolean = LocalDateTime.now().isAfter(expiresAt)
}
