package com.boaz.lp.api.auth.jwt

import com.boaz.lp.common.enum.Role
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthFilter(private val jwtProvider: JwtProvider) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractToken(request)

        if (token != null && jwtProvider.validateToken(token) == TokenValidationResult.VALID) {
            val memberId = jwtProvider.getMemberId(token)
            val role = jwtProvider.getRole(token)
            setAuthentication(memberId, role)
        }

        filterChain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val header = request.getHeader("Authorization") ?: return null
        if (!header.startsWith("Bearer ")) return null
        return header.removePrefix("Bearer ")
    }

    private fun setAuthentication(memberId: Long, role: Role) {
        val authorities = listOf(SimpleGrantedAuthority("ROLE_${role.name}"))
        val auth = UsernamePasswordAuthenticationToken(memberId, null, authorities)
        SecurityContextHolder.getContext().authentication = auth
    }
}
