package com.boaz.lp.api.auth.jwt

import com.boaz.lp.common.enum.Role
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-expiry-ms}") private val accessTokenExpiryMs: Long
) {

    private val key: SecretKey by lazy {
        Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun generateAccessToken(memberId: Long, role: Role): String {
        val now = Date()
        return Jwts.builder()
            .subject(memberId.toString())
            .claim("role", role.name)
            .issuedAt(now)
            .expiration(Date(now.time + accessTokenExpiryMs))
            .signWith(key)
            .compact()
    }

    fun getMemberId(token: String): Long {
        return parseClaims(token).subject.toLong()
    }

    fun getRole(token: String): Role {
        return Role.valueOf(parseClaims(token).get("role", String::class.java))
    }

    fun validateToken(token: String): TokenValidationResult {
        return try {
            parseClaims(token)
            TokenValidationResult.VALID
        } catch (e: ExpiredJwtException) {
            TokenValidationResult.EXPIRED
        } catch (e: Exception) {
            TokenValidationResult.INVALID
        }
    }

    private fun parseClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}

enum class TokenValidationResult {
    VALID, EXPIRED, INVALID
}
