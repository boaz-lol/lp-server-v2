package com.lp.v2.domains.auth.service;

import com.lp.v2.domains.auth.dto.TokenPair;
import com.lp.v2.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class JwtService {
    private final JwtProvider jwtProvider;

    TokenPair create(Long accountId) {
        return new TokenPair (
                jwtProvider.createAccessToken(accountId.toString()),
                jwtProvider.createRefreshToken(accountId.toString())
        );
    }

}
