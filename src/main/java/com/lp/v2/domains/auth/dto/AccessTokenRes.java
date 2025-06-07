package com.lp.v2.domains.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenRes {
    private String accessToken;
    private String tokenType;

    public static AccessTokenRes of(String accessToken) {
        return AccessTokenRes.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .build();
    }
}
