package com.boaz.lp.common.dto.response

import com.boaz.lp.common.enum.QueueType
import java.time.LocalDateTime

/**
 * Match summary response
 */
data class MatchResponse(
    val matchId: String,
    val queueType: QueueType,
    val gameCreation: LocalDateTime,
    val gameDuration: Int,  // in seconds
    val participants: List<ParticipantResponse>,
    val teams: List<TeamResponse>
)

/**
 * Participant in a match
 */
data class ParticipantResponse(
    val puuid: String,
    val summonerName: String,
    val championId: Int,
    val championName: String,
    val teamId: Int,
    val win: Boolean,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val totalDamageDealt: Int,
    val goldEarned: Int,
    val items: List<Int>
) {
    val kda: Double
        get() = if (deaths == 0) (kills + assists).toDouble() else (kills + assists).toDouble() / deaths
}

/**
 * Team summary in a match
 */
data class TeamResponse(
    val teamId: Int,
    val win: Boolean,
    val totalKills: Int,
    val totalGold: Int,
    val objectives: ObjectivesResponse
)

/**
 * Objectives (dragon, baron, towers, etc.)
 */
data class ObjectivesResponse(
    val baron: Int,
    val dragon: Int,
    val tower: Int,
    val inhibitor: Int
)
