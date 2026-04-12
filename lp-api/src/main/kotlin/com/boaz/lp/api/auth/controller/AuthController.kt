package com.boaz.lp.api.auth.controller

import com.boaz.lp.api.auth.service.AuthService
import com.boaz.lp.common.dto.ApiResponse
import com.boaz.lp.common.exception.BusinessException
import com.boaz.lp.common.exception.ErrorCode
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/refresh")
    fun refresh(
        @CookieValue("refresh_token", required = false) refreshToken: String?,
        response: HttpServletResponse
    ): ApiResponse<Map<String, String>> {
        if (refreshToken == null) throw BusinessException(ErrorCode.TOKEN_NOT_FOUND)
        val accessToken = authService.refresh(refreshToken)
        return ApiResponse.success(mapOf("accessToken" to accessToken))
    }

    @PostMapping("/logout")
    fun logout(
        @CookieValue("refresh_token", required = false) refreshToken: String?,
        response: HttpServletResponse
    ): ApiResponse<Unit> {
        authService.logout(refreshToken)
        response.addCookie(Cookie("refresh_token", "").apply {
            isHttpOnly = true
            secure = true
            path = "/auth/refresh"
            maxAge = 0
        })
        return ApiResponse.success(Unit)
    }
}
