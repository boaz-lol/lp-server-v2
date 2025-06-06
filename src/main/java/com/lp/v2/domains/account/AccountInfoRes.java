package com.lp.v2.domains.account;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountInfoRes {
    private final String email;
    private final String username;

    public static AccountInfoRes fromEntity(AccountEntity entity) {
        return AccountInfoRes.builder()
            .email(entity.getEmail())
            .username(entity.getUsername())
            .build();
    }
}
