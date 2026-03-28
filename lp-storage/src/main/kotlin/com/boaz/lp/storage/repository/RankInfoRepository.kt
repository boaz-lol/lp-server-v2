package com.boaz.lp.storage.repository

import com.boaz.lp.storage.entity.RankInfoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RankInfoRepository : JpaRepository<RankInfoEntity, Long> {

    fun findBySummonerId(summonerId: Long): List<RankInfoEntity>

    fun findBySummonerIdAndQueueType(summonerId: Long, queueType: String): RankInfoEntity?
}
