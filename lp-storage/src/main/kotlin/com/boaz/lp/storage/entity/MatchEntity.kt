package com.boaz.lp.storage.entity

import com.boaz.lp.storage.entity.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "match_info",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_match_match_id", columnNames = ["match_id"])
    ]
)
class MatchEntity(

    @Column(name = "match_id", nullable = false)
    var matchId: String,

    @Column(name = "game_creation", nullable = false)
    var gameCreation: Long,

    @Column(name = "game_duration", nullable = false)
    var gameDuration: Long,

    @Column(name = "game_mode")
    var gameMode: String? = null,

    @Column(name = "queue_id")
    var queueId: Int = 0,

    @Column(name = "game_version")
    var gameVersion: String? = null,

    @Column(name = "platform_id")
    var platformId: String? = null,

    @OneToMany(mappedBy = "match", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var participants: MutableList<MatchParticipantEntity> = mutableListOf()

) : BaseEntity()
