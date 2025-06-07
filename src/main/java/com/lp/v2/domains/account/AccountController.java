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

    @PostMapping("/verify")
    public ResponseEntity<BaseResponse> verifyPassword(
            @CurrentAccountId Long accountId,
            @RequestBody PasswordVerifyReq req
    ) {
        accountService.verifyPassword(accountId, req.password());
        return ResponseEntity.ok(
                BaseResponse.success(ResponseMessage.PASSWORD_VERIFICATION_SUCCESS)
        );
    }

    @PutMapping("/me")
    public ResponseEntity<BaseResponse> updateMyInfo(
            @CurrentAccountId Long accountId,
            @RequestBody AccountUpdateReq req
    ) {
        accountService.update(accountId, req);
        return ResponseEntity.ok(
                BaseResponse.success(ResponseMessage.ACCOUNT_UPDATE_SUCCESS)
        );
    }

    @DeleteMapping("/me")
    public ResponseEntity<BaseResponse> deleteMyAccount(@CurrentAccountId Long accountId) {
        accountService.delete(accountId);
        return ResponseEntity.ok(
                BaseResponse.success(ResponseMessage.ACCOUNT_DELETE_SUCCESS)
        );
    }

}
