package com.boaz.lp.storage.repository

import com.boaz.lp.storage.entity.RefreshTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, Long> {
    fun findByToken(token: String): Optional<RefreshTokenEntity>
    fun deleteByMemberId(memberId: Long)
    fun deleteByToken(token: String)
}
