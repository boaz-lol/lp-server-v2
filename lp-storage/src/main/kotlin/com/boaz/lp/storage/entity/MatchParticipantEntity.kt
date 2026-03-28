package com.boaz.lp.storage.entity

import com.boaz.lp.storage.entity.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "match_participant",
    indexes = [
        Index(name = "idx_participant_puuid", columnList = "puuid"),
        Index(name = "idx_participant_match_id", columnList = "match_id")
    ]
)
class MatchParticipantEntity(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    var match: MatchEntity,

    @Column(name = "puuid", nullable = false, length = 78)
    var puuid: String,

    @Column(name = "champion_id")
    var championId: Int = 0,

    @Column(name = "champion_name")
    var championName: String? = null,

    @Column(name = "team_id")
    var teamId: Int = 0,

    @Column(name = "win")
    var win: Boolean = false,

    @Column(name = "kills")
    var kills: Int = 0,

    @Column(name = "deaths")
    var deaths: Int = 0,

    @Column(name = "assists")
    var assists: Int = 0,

    @Column(name = "total_damage_dealt_to_champions")
    var totalDamageDealtToChampions: Long = 0,

    @Column(name = "gold_earned")
    var goldEarned: Int = 0,

    @Column(name = "total_minions_killed")
    var totalMinionsKilled: Int = 0,

    @Column(name = "vision_score")
    var visionScore: Int = 0,

    @Column(name = "individual_position")
    var individualPosition: String? = null,

    @Column(name = "item0")
    var item0: Int = 0,

    @Column(name = "item1")
    var item1: Int = 0,

    @Column(name = "item2")
    var item2: Int = 0,

    @Column(name = "item3")
    var item3: Int = 0,

    @Column(name = "item4")
    var item4: Int = 0,

    @Column(name = "item5")
    var item5: Int = 0,

    @Column(name = "item6")
    var item6: Int = 0

) : BaseEntity()
