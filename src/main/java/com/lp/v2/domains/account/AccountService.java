package com.lp.v2.domains.account;

import com.lp.v2.security.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
    private final AccountJpaRepository accountRepository;
    private final PasswordService passwordService;

    public void create(AccountCreateReq req) {
        if (accountRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("이미 가입한 이메일입니다.");
        }

        accountRepository.save(
                AccountEntity.builder()
                        .email(req.email())
                        .password(passwordService.encodePassword(req.password()))
                        .username(req.username())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public Long getByEmailAndPassword(String email, String password) {
        AccountEntity account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("계정을 찾을 수 없습니다."));

        if (!passwordService.verifyPassword(password, account.getPassword())) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }

        return account.getId();
    }

    @Transactional(readOnly = true)
    public AccountInfoRes getInfo(Long accountId) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("계정을 찾을 수 없습니다."));

        return AccountInfoRes.fromEntity(account);
    }

    @Transactional(readOnly = true)
    public void verifyPassword(Long accountId, String password) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("계정을 찾을 수 없습니다."));

        if (!passwordService.verifyPassword(password, account.getPassword())) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }
    }

    public void update(Long accountId, AccountUpdateReq req) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("계정을 찾을 수 없습니다."));

        if (req.username() != null) {
            account.updateUsername(req.username());
        }

        if (req.existingPassword() != null && req.newPassword() != null) {
            if (!passwordService.verifyPassword(req.existingPassword(), account.getPassword())) {
                throw new IllegalArgumentException("기존 패스워드가 일치하지 않습니다.");
            }
            account.updatePassword(passwordService.encodePassword(req.newPassword()));
        }

        accountRepository.save(account);
    }
}
