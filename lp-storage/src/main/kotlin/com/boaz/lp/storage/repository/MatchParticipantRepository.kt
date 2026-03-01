package com.boaz.lp.storage.repository

import com.boaz.lp.storage.entity.MatchParticipantEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface MatchParticipantRepository : JpaRepository<MatchParticipantEntity, Long> {

    fun findByPuuid(puuid: String, pageable: Pageable): Page<MatchParticipantEntity>
}
