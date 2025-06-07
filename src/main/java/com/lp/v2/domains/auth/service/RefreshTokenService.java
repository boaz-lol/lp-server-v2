package com.lp.v2.domains.auth.service;

import com.lp.v2.domains.auth.core.RefreshTokenEntity;
import com.lp.v2.domains.auth.core.RefreshTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RefreshTokenService {
    private final RefreshTokenJpaRepository refreshTokenRepository;

    RefreshTokenEntity create(String token, Long accountId) {
        return refreshTokenRepository.save(
                RefreshTokenEntity.builder()
                        .token(token)
                        .accountId(accountId)
                        .build()
        );
    }

    void validateRefreshToken(String token) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 refresh token입니다."));

    }

    Long getAccountIdFromToken(String token) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 refresh token입니다."));
        return refreshToken.getAccountId();
    }

    void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

}
