package com.boaz.lp.api.auth.oauth

import com.boaz.lp.api.auth.jwt.JwtProvider
import com.boaz.lp.common.enum.Role
import com.boaz.lp.storage.entity.RefreshTokenEntity
import com.boaz.lp.storage.repository.RefreshTokenRepository
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

@Component
class OAuth2SuccessHandler(
    private val jwtProvider: JwtProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    @Value("\${oauth2.redirect-uri}") private val redirectUri: String,
    @Value("\${jwt.refresh-token-expiry-days}") private val refreshTokenExpiryDays: Long
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val oAuth2User = authentication.principal as OAuth2User
        val memberId = oAuth2User.attributes["memberId"] as Long
        val role = Role.valueOf(oAuth2User.attributes["role"] as String)

        val accessToken = jwtProvider.generateAccessToken(memberId, role)
        val refreshToken = issueRefreshToken(memberId)

        addRefreshTokenCookie(response, refreshToken)
        response.sendRedirect("$redirectUri?token=$accessToken")
    }

    private fun issueRefreshToken(memberId: Long): String {
        refreshTokenRepository.deleteByMemberId(memberId)

        val token = UUID.randomUUID().toString()
        refreshTokenRepository.save(
            RefreshTokenEntity(
                memberId = memberId,
                token = token,
                expiresAt = LocalDateTime.now().plusDays(refreshTokenExpiryDays)
            )
        )
        return token
    }

    private fun addRefreshTokenCookie(response: HttpServletResponse, token: String) {
        val cookie = Cookie("refresh_token", token).apply {
            isHttpOnly = true
            secure = true
            path = "/auth/refresh"
            maxAge = (refreshTokenExpiryDays * 24 * 60 * 60).toInt()
        }
        response.addCookie(cookie)
    }
}
