package com.boaz.lp.storage.repository

import com.boaz.lp.common.enum.OAuthProvider
import com.boaz.lp.storage.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MemberRepository : JpaRepository<MemberEntity, Long> {
    fun findByProviderAndProviderId(provider: OAuthProvider, providerId: String): Optional<MemberEntity>
    fun findByEmail(email: String): Optional<MemberEntity>
}
