package com.boaz.lp.storage.repository

import com.boaz.lp.storage.entity.MatchEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MatchRepository : JpaRepository<MatchEntity, Long> {

    fun findByMatchId(matchId: String): MatchEntity?

    fun existsByMatchId(matchId: String): Boolean
}
