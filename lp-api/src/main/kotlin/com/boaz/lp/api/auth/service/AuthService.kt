package com.boaz.lp.api.auth.service

import com.boaz.lp.api.auth.jwt.JwtProvider
import com.boaz.lp.common.exception.BusinessException
import com.boaz.lp.common.exception.ErrorCode
import com.boaz.lp.storage.repository.MemberRepository
import com.boaz.lp.storage.repository.RefreshTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val jwtProvider: JwtProvider,
    private val memberRepository: MemberRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun refresh(refreshToken: String): String {
        val tokenEntity = refreshTokenRepository.findByToken(refreshToken)
            .orElseThrow { BusinessException(ErrorCode.INVALID_TOKEN) }

        if (tokenEntity.isExpired()) {
            refreshTokenRepository.delete(tokenEntity)
            throw BusinessException(ErrorCode.EXPIRED_TOKEN)
        }

        val member = memberRepository.findById(tokenEntity.memberId)
            .orElseThrow { BusinessException(ErrorCode.MEMBER_NOT_FOUND) }

        return jwtProvider.generateAccessToken(member.id!!, member.role)
    }

    fun logout(refreshToken: String?) {
        if (refreshToken != null) {
            refreshTokenRepository.deleteByToken(refreshToken)
        }
    }
}