package com.lp.v2.domains.account;

import com.lp.v2.common.response.BaseResponse;
import com.lp.v2.common.response.ResponseMessage;
import com.lp.v2.security.annotation.CurrentAccountId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody AccountCreateReq req) {
        accountService.create(req);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public AccountInfoRes getMyInfo(@CurrentAccountId Long accountId) {
        return accountService.getInfo(accountId);
    }

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public void verifyPassword(
            @CurrentAccountId Long accountId,
            @RequestBody PasswordVerifyReq req
    ) {
        accountService.verifyPassword(accountId, req.password());
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public void updateMyInfo(
            @CurrentAccountId Long accountId,
            @RequestBody AccountUpdateReq req
    ) {
        accountService.update(accountId, req);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMyAccount(@CurrentAccountId Long accountId) {
        accountService.delete(accountId);
    }

}
