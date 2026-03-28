package com.boaz.lp.batch.client.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class RiotAccountDto(
    val puuid: String,
    val gameName: String?,
    val tagLine: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RiotSummonerDto(
    val id: String? = null,
    val accountId: String? = null,
    val puuid: String,
    val profileIconId: Int,
    val summonerLevel: Long
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RiotMatchDto(
    val metadata: RiotMatchMetadataDto,
    val info: RiotMatchInfoDto
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RiotMatchMetadataDto(
    val matchId: String,
    val participants: List<String>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RiotMatchInfoDto(
    val gameCreation: Long,
    val gameDuration: Long,
    val gameMode: String?,
    val queueId: Int,
    val gameVersion: String?,
    val platformId: String?,
    val participants: List<RiotParticipantDto>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RiotParticipantDto(
    val puuid: String,
    val championId: Int,
    val championName: String?,
    val teamId: Int,
    val win: Boolean,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val totalDamageDealtToChampions: Long,
    val goldEarned: Int,
    val totalMinionsKilled: Int,
    val visionScore: Int,
    val individualPosition: String?,
    val item0: Int,
    val item1: Int,
    val item2: Int,
    val item3: Int,
    val item4: Int,
    val item5: Int,
    val item6: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RiotLeagueEntryDto(
    val queueType: String,
    val tier: String?,
    val rank: String?,
    val leaguePoints: Int,
    val wins: Int,
    val losses: Int,
    val hotStreak: Boolean,
    val veteran: Boolean,
    val freshBlood: Boolean,
    val inactive: Boolean
)
