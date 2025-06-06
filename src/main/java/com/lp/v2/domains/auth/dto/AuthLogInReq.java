package com.lp.v2.domains.auth.dto;

public record AuthLogInReq(
        String email,
        String password
) {}
