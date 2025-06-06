package com.lp.v2.domains.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthLogInRes {
    private String accessToken;
    private String tokenType;

    public static AuthLogInRes of(String accessToken) {
        return AuthLogInRes.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .build();
    }
}
