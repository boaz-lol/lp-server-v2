package com.lp.v2.domains.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
    private final AccountJpaRepository accountRepository;

    public void create(AccountCreateReq req) {
        if(accountRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("이미 가입한 이메일입니다.");
        }

        accountRepository.save(
                AccountEntity.builder()
                        .email(req.email())
                        .password(req.password())
                        .username(req.username())
                        .build()
        );
    }
}
