package com.boaz.lp.batch.mapper

import com.boaz.lp.batch.client.dto.*
import com.boaz.lp.common.enum.RiotRegion
import com.boaz.lp.storage.entity.*
import org.springframework.stereotype.Component

@Component
class RiotDataMapper {

    fun toSummonerEntity(
        account: RiotAccountDto,
        summoner: RiotSummonerDto,
        region: RiotRegion
    ): SummonerEntity {
        return SummonerEntity(
            puuid = account.puuid,
            summonerId = summoner.id ?: summoner.puuid,
            gameName = account.gameName ?: "",
            tagLine = account.tagLine ?: "",
            profileIconId = summoner.profileIconId,
            summonerLevel = summoner.summonerLevel,
            region = region
        )
    }

    fun updateSummonerEntity(
        entity: SummonerEntity,
        account: RiotAccountDto,
        summoner: RiotSummonerDto
    ): SummonerEntity {
        entity.gameName = account.gameName ?: entity.gameName
        entity.tagLine = account.tagLine ?: entity.tagLine
        entity.summonerId = summoner.id ?: summoner.puuid
        entity.profileIconId = summoner.profileIconId
        entity.summonerLevel = summoner.summonerLevel
        return entity
    }

    fun toMatchEntity(matchDto: RiotMatchDto): MatchEntity {
        val info = matchDto.info
        val match = MatchEntity(
            matchId = matchDto.metadata.matchId,
            gameCreation = info.gameCreation,
            gameDuration = info.gameDuration,
            gameMode = info.gameMode,
            queueId = info.queueId,
            gameVersion = info.gameVersion,
            platformId = info.platformId
        )

        val participants = info.participants.map { p ->
            toMatchParticipantEntity(p, match)
        }.toMutableList()

        match.participants = participants
        return match
    }

    private fun toMatchParticipantEntity(dto: RiotParticipantDto, match: MatchEntity): MatchParticipantEntity {
        return MatchParticipantEntity(
            match = match,
            puuid = dto.puuid,
            championId = dto.championId,
            championName = dto.championName,
            teamId = dto.teamId,
            win = dto.win,
            kills = dto.kills,
            deaths = dto.deaths,
            assists = dto.assists,
            totalDamageDealtToChampions = dto.totalDamageDealtToChampions,
            goldEarned = dto.goldEarned,
            totalMinionsKilled = dto.totalMinionsKilled,
            visionScore = dto.visionScore,
            individualPosition = dto.individualPosition,
            item0 = dto.item0,
            item1 = dto.item1,
            item2 = dto.item2,
            item3 = dto.item3,
            item4 = dto.item4,
            item5 = dto.item5,
            item6 = dto.item6
        )
    }

    fun toRankInfoEntity(dto: RiotLeagueEntryDto, summoner: SummonerEntity): RankInfoEntity {
        return RankInfoEntity(
            summoner = summoner,
            queueType = dto.queueType,
            tier = dto.tier,
            division = dto.rank,
            leaguePoints = dto.leaguePoints,
            wins = dto.wins,
            losses = dto.losses,
            hotStreak = dto.hotStreak,
            veteran = dto.veteran,
            freshBlood = dto.freshBlood,
            inactive = dto.inactive
        )
    }

    fun updateRankInfoEntity(entity: RankInfoEntity, dto: RiotLeagueEntryDto): RankInfoEntity {
        entity.tier = dto.tier
        entity.division = dto.rank
        entity.leaguePoints = dto.leaguePoints
        entity.wins = dto.wins
        entity.losses = dto.losses
        entity.hotStreak = dto.hotStreak
        entity.veteran = dto.veteran
        entity.freshBlood = dto.freshBlood
        entity.inactive = dto.inactive
        return entity
    }
}
