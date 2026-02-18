package com.boaz.lp.common.dto.request

import com.boaz.lp.common.enum.RiotRegion
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Request to search/create summoner
 */
data class SummonerSearchRequest(
    @field:NotBlank(message = "Summoner name is required")
    @field:Size(min = 3, max = 16, message = "Summoner name must be between 3 and 16 characters")
    val summonerName: String,

    val region: RiotRegion = RiotRegion.KR,

    val forceUpdate: Boolean = false  // Force fetch from Riot API
)

/**
 * Request to get match history
 */
data class MatchHistoryRequest(
    @field:NotBlank(message = "PUUID is required")
    val puuid: String,

    val start: Int = 0,

    val count: Int = 20
)
