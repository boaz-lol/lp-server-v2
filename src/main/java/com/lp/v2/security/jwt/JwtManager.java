package com.lp.v2.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtManager implements JwtProvider, JwtValidator {

    private final JwtKeyFactory jwtKeyFactory;

    @Value("${spring.security.jwt.access-expiration-ms}")
    private long accessExpirationTime;

    @Value("${spring.security.jwt.refresh-expiration-ms}")
    private long refreshExpirationTime;

    @Override
    public String createAccessToken(String subject) {
        return createToken(subject, accessExpirationTime);
    }

    @Override
    public String createRefreshToken(String subject) {
        return createToken(subject, refreshExpirationTime);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            log.warn("Failed to parse token for expiration check: {}", e.getMessage());
            return true; // 유효하지 않은 토큰은 만료된 것으로 간주
        }
    }

    @Override
    public String getSubject(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.getSubject();
        } catch (JwtException e) {
            log.warn("Failed to extract subject from token: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid token", e);
        }
    }

    private String createToken(String subject, long expirationTime) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtKeyFactory.getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtKeyFactory.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}