package com.lp.v2.security.jwt;

public interface JwtValidator {
    boolean validateToken(String token);
    boolean isTokenExpired(String token);
    String getSubject(String token);
}
