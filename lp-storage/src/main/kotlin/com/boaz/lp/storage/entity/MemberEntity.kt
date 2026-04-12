package com.boaz.lp.storage.entity

import com.boaz.lp.common.enum.MemberStatus
import com.boaz.lp.common.enum.OAuthProvider
import com.boaz.lp.common.enum.Role
import com.boaz.lp.storage.entity.base.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "member",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_member_provider_provider_id", columnNames = ["provider", "provider_id"])
    ],
    indexes = [
        Index(name = "idx_member_email", columnList = "email")
    ]
)
class MemberEntity(

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, length = 20)
    var provider: OAuthProvider,

    @Column(name = "provider_id", nullable = false)
    var providerId: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "nickname", nullable = false)
    var nickname: String,

    @Column(name = "profile_image_url")
    var profileImageUrl: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    var role: Role = Role.USER,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    var status: MemberStatus = MemberStatus.ACTIVE,

    @Column(name = "last_login_at")
    var lastLoginAt: LocalDateTime? = null

) : BaseEntity()
