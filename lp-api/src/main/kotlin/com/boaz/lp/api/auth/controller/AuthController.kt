package com.boaz.lp.api.auth.controller

import com.boaz.lp.api.auth.jwt.JwtProvider
import com.boaz.lp.api.auth.jwt.TokenValidationResult
import com.boaz.lp.common.dto.ApiResponse
import com.boaz.lp.common.exception.BusinessException
import com.boaz.lp.common.exception.ErrorCode
import com.boaz.lp.storage.entity.RefreshTokenEntity
import com.boaz.lp.storage.repository.MemberRepository
import com.boaz.lp.storage.repository.RefreshTokenRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.UUID

@RestController
@RequestMapping("/auth")
class AuthController(
    private val jwtProvider: JwtProvider,
    private val memberRepository: MemberRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    @PostMapping("/refresh")
    fun refresh(
        @CookieValue("refresh_token", required = false) refreshToken: String?,
        response: HttpServletResponse
    ): ApiResponse<Map<String, String>> {
        if (refreshToken == null) throw BusinessException(ErrorCode.TOKEN_NOT_FOUND)

        val tokenEntity = refreshTokenRepository.findByToken(refreshToken)
            .orElseThrow { BusinessException(ErrorCode.INVALID_TOKEN) }

        if (tokenEntity.isExpired()) {
            refreshTokenRepository.delete(tokenEntity)
            throw BusinessException(ErrorCode.EXPIRED_TOKEN)
        }

        val member = memberRepository.findById(tokenEntity.memberId)
            .orElseThrow { BusinessException(ErrorCode.MEMBER_NOT_FOUND) }

        val newAccessToken = jwtProvider.generateAccessToken(member.id!!, member.role)
        return ApiResponse.success(mapOf("accessToken" to newAccessToken))
    }

    @PostMapping("/logout")
    fun logout(
        @CookieValue("refresh_token", required = false) refreshToken: String?,
        response: HttpServletResponse
    ): ApiResponse<Unit> {
        if (refreshToken != null) {
            refreshTokenRepository.deleteByToken(refreshToken)
        }

        val expiredCookie = jakarta.servlet.http.Cookie("refresh_token", "").apply {
            isHttpOnly = true
            secure = true
            path = "/auth/refresh"
            maxAge = 0
        }
        response.addCookie(expiredCookie)

        return ApiResponse.success(Unit)
    }
}
