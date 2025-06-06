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
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public TokenPair logIn(AuthLogInReq req) {
        Long accountId = accountService.getByEmailAndPassword(req.email(), req.password());
        TokenPair tokenPair = jwtService.create(accountId);
        refreshTokenService.create(tokenPair.refreshToken(), accountId);
        return tokenPair;
    }

}
