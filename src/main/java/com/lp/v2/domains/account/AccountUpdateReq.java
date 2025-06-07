package com.lp.v2.domains.account;

public record AccountUpdateReq(
        String username,
        String existingPassword,
        String newPassword
) {
}
