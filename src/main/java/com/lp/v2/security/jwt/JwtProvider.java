package com.lp.v2.security.jwt;

public interface JwtProvider {
    String createAccessToken(String subject);
    String createRefreshToken(String subject);
}
