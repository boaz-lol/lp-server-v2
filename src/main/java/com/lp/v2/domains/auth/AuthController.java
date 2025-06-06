package com.lp.v2.domains.auth;

import com.lp.v2.domains.auth.dto.AuthLogInReq;
import com.lp.v2.domains.auth.dto.AuthLogInRes;
import com.lp.v2.domains.auth.dto.TokenPair;
import com.lp.v2.domains.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthLogInRes> logIn (@RequestBody AuthLogInReq req) {
        TokenPair tokenPair = authService.logIn(req);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokenPair.refreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(604800000)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(AuthLogInRes.of(tokenPair.accessToken()));
    }

}
