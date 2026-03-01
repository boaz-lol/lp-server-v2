package com.boaz.lp.storage.entity

import com.boaz.lp.storage.entity.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "rank_info",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_rank_summoner_queue", columnNames = ["summoner_id", "queue_type"])
    ]
)
class RankInfoEntity(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "summoner_id", nullable = false)
    var summoner: SummonerEntity,

    @Column(name = "queue_type", nullable = false)
    var queueType: String,

    @Column(name = "tier")
    var tier: String? = null,

    @Column(name = "division")
    var division: String? = null,

    @Column(name = "league_points")
    var leaguePoints: Int = 0,

    @Column(name = "wins")
    var wins: Int = 0,

    @Column(name = "losses")
    var losses: Int = 0,

    @Column(name = "hot_streak")
    var hotStreak: Boolean = false,

    @Column(name = "veteran")
    var veteran: Boolean = false,

    @Column(name = "fresh_blood")
    var freshBlood: Boolean = false,

    @Column(name = "inactive")
    var inactive: Boolean = false

) : BaseEntity()
