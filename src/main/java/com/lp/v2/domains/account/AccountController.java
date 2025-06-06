package com.lp.v2.domains.account;

import com.lp.v2.common.response.BaseResponse;
import com.lp.v2.common.response.ResponseMessage;
import com.lp.v2.security.annotation.CurrentAccountId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> createAccount(@RequestBody AccountCreateReq req) {
        accountService.create(req);
        return ResponseEntity.ok(
                BaseResponse.success(ResponseMessage.ACCOUNT_CREATED_SUCCESS)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<AccountInfoRes> getMyInfo(@CurrentAccountId Long accountId) {
        return ResponseEntity.ok().body(accountService.getInfo(accountId));
    }

}
