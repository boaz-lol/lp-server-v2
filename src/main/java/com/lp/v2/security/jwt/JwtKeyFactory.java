package com.lp.v2.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@Getter
@Component
@Slf4j
public class JwtKeyFactory {

    private final Key secretKey;

    public JwtKeyFactory(@Value("${spring.security.jwt.secret}") String secretKeyString) {
        this.secretKey = initializeKey(secretKeyString);
        log.info("JWT secret key initialized successfully");
    }

    private Key initializeKey(String secretKeyString) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKeyString);
            log.debug("Using Base64 decoded secret key");
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            log.warn("Secret key is not Base64 encoded, using UTF-8 bytes directly");
            byte[] keyBytes = secretKeyString.getBytes(StandardCharsets.UTF_8);
            return Keys.hmacShaKeyFor(keyBytes);
        }
    }

}