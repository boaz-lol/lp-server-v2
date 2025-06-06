package com.lp.v2.domains.account;



public record AccountCreateReq (
    String email,
    String username,
    String password
) {}
