package com.boaz.lp.storage.entity

import com.boaz.lp.common.enum.RiotRegion
import com.boaz.lp.storage.entity.base.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "summoner",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_summoner_puuid", columnNames = ["puuid"]),
        UniqueConstraint(name = "uk_summoner_summoner_id", columnNames = ["summoner_id"])
    ],
    indexes = [
        Index(name = "idx_summoner_game_name_tag_region", columnList = "game_name, tag_line, region")
    ]
)
class SummonerEntity(

    @Column(name = "puuid", nullable = false, length = 78)
    var puuid: String,

    @Column(name = "summoner_id", nullable = false)
    var summonerId: String,

    @Column(name = "game_name", nullable = false)
    var gameName: String,

    @Column(name = "tag_line", nullable = false)
    var tagLine: String,

    @Column(name = "profile_icon_id")
    var profileIconId: Int = 0,

    @Column(name = "summoner_level")
    var summonerLevel: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    var region: RiotRegion,

    @Column(name = "last_match_collected_at")
    var lastMatchCollectedAt: LocalDateTime? = null

) : BaseEntity()
