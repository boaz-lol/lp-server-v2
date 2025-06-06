package com.lp.v2.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${spring.security.jwt.secret}")
    private String secretKeyString;

    @Value("${spring.security.jwt.access-expiration-ms}")
    private long accessExpirationTime;

    @Value("${spring.security.jwt.refresh-expiration-ms}")
    private long refreshExpirationTime;

    private Key secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = secretKeyString.getBytes();
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessExpirationTime);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshExpirationTime);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getSubject(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException ex) {
            throw new IllegalArgumentException("Invalid JWT signature: " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            throw new IllegalArgumentException("Expired JWT token: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            throw new IllegalArgumentException("Unsupported JWT token: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }


    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
