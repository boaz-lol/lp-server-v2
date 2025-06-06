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
}
