package com.lp.v2.domains.account;

import com.lp.v2.common.response.BaseResponse;
import com.lp.v2.common.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<BaseResponse> createAccount(@RequestBody AccountCreateReq req) {
        accountService.create(req);
        return ResponseEntity.ok(
                BaseResponse.success(ResponseMessage.ACCOUNT_CREATED_SUCCESS)
        );
    }
}
