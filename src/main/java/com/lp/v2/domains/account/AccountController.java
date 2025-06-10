package com.lp.v2.domains.account;

import com.lp.v2.common.response.BaseResponse;
import com.lp.v2.common.response.ResponseMessage;
import com.lp.v2.common.response.SuccessResponse;
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
    public ResponseEntity<?> createAccount(@RequestBody AccountCreateReq req) {
        accountService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(ResponseMessage.ACCOUNT_CREATED_SUCCESS));
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountInfoRes> getMyInfo(@CurrentAccountId Long accountId) {
        return ResponseEntity.ok(accountService.getInfo(accountId));
    }

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<?> verifyPassword(
            @CurrentAccountId Long accountId,
            @RequestBody PasswordVerifyReq req
    ) {
        accountService.verifyPassword(accountId, req.password());
        return SuccessResponse.ok(ResponseMessage.SUCCESS);
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<?> updateMyInfo(
            @CurrentAccountId Long accountId,
            @RequestBody AccountUpdateReq req
    ) {
        accountService.update(accountId, req);
        return SuccessResponse.ok(ResponseMessage.ACCOUNT_UPDATE_SUCCESS);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<?> deleteMyAccount(@CurrentAccountId Long accountId) {
        accountService.delete(accountId);
        return SuccessResponse.ok(ResponseMessage.ACCOUNT_DELETE_SUCCESS);
    }

}
