package com.lp.v2.domains.auth.service;

import com.lp.v2.domains.auth.dto.TokenPair;
import com.lp.v2.security.jwt.JwtManager;
import com.lp.v2.security.jwt.JwtProvider;
import com.lp.v2.security.jwt.JwtValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class JwtAuthService {
    private final JwtValidator jwtValidator;
    private final JwtProvider jwtProvider;
    private static final String BEARER_PREFIX = "Bearer ";

    TokenPair create(Long accountId) {
        return new TokenPair (
                BEARER_PREFIX + jwtProvider.createAccessToken(accountId.toString()),
                jwtProvider.createRefreshToken(accountId.toString())
        );
    }


    public Long getAccountIdFromToken(String refreshToken) {
        if (!jwtValidator.validateToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 refresh token입니다.");
        }
        String accountId = jwtValidator.getSubject(refreshToken);
        return Long.parseLong(accountId);
    }
}
