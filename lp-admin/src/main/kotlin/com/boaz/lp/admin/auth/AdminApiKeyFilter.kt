package com.boaz.lp.admin.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.filter.OncePerRequestFilter

class AdminApiKeyFilter(private val apiKey: String) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestKey = request.getHeader("X-Admin-Key")

        if (requestKey == null || requestKey != apiKey) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid or missing admin API key")
            return
        }

        filterChain.doFilter(request, response)
    }
}
