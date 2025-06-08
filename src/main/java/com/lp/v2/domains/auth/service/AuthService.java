package com.lp.v2.domains.auth.service;

import com.lp.v2.domains.account.AccountService;
import com.lp.v2.domains.auth.dto.AuthLogInReq;
import com.lp.v2.domains.auth.dto.TokenPair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AccountService accountService;
    private final JwtAuthService jwtAuthService;
    private final RefreshTokenService refreshTokenService;

    public TokenPair logIn(AuthLogInReq req) {
        Long accountId = accountService.getByEmailAndPassword(req.email(), req.password());
        TokenPair tokenPair = jwtAuthService.create(accountId);
        refreshTokenService.create(tokenPair.refreshToken(), accountId);
        return tokenPair;
    }


    public TokenPair reissueTokens(String refreshToken) {
        // refresh token 유효성 검증
        refreshTokenService.validateRefreshToken(refreshToken);

        // refresh token에서 사용자 정보 추출
        Long accountId = jwtAuthService.getAccountIdFromToken(refreshToken);

        // 새로운 토큰 쌍 생성
        TokenPair newTokenPair = jwtAuthService.create(accountId);

        // 기존 refresh token 삭제하고 새로운 refresh token 저장
        refreshTokenService.deleteByToken(refreshToken);
        refreshTokenService.create(newTokenPair.refreshToken(), accountId);

        return newTokenPair;
    }

}
