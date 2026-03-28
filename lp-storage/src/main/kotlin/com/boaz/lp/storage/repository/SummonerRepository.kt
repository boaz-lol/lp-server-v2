package com.boaz.lp.storage.repository

import com.boaz.lp.common.enum.RiotRegion
import com.boaz.lp.storage.entity.SummonerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SummonerRepository : JpaRepository<SummonerEntity, Long> {

    fun findByPuuid(puuid: String): SummonerEntity?

    fun findByGameNameAndTagLineAndRegion(gameName: String, tagLine: String, region: RiotRegion): SummonerEntity?

    fun findAllByRegion(region: RiotRegion): List<SummonerEntity>
}
