package com.lp.v2.domains.auth.service;

import com.lp.v2.domains.auth.dto.TokenPair;
import com.lp.v2.security.jwt.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class JwtAuthService {
    private final JwtManager jwtManager;
    private static final String BEARER_PREFIX = "Bearer ";

    TokenPair create(Long accountId) {
        return new TokenPair (
                BEARER_PREFIX + jwtManager.createAccessToken(accountId.toString()),
                jwtManager.createRefreshToken(accountId.toString())
        );
    }

}
