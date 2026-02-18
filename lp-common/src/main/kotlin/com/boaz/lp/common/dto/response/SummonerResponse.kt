package com.boaz.lp.common.dto.response

import com.boaz.lp.common.enum.Rank
import com.boaz.lp.common.enum.RiotRegion
import com.boaz.lp.common.enum.Tier
import java.time.LocalDateTime

/**
 * Summoner basic information response
 */
data class SummonerResponse(
    val id: Long,
    val puuid: String,
    val summonerId: String,  // Encrypted summoner ID from Riot API
    val summonerName: String,
    val profileIconId: Int,
    val summonerLevel: Int,
    val region: RiotRegion,
    val lastUpdated: LocalDateTime
)

/**
 * Summoner with rank information
 */
data class SummonerDetailResponse(
    val summoner: SummonerResponse,
    val rankedInfo: RankedInfoResponse?
)

/**
 * Ranked information
 */
data class RankedInfoResponse(
    val soloRank: RankDetailResponse?,
    val flexRank: RankDetailResponse?
)

/**
 * Rank detail for a specific queue
 */
data class RankDetailResponse(
    val tier: Tier,
    val rank: Rank,
    val leaguePoints: Int,
    val wins: Int,
    val losses: Int,
    val winRate: Double
) {
    val totalGames: Int
        get() = wins + losses
}
