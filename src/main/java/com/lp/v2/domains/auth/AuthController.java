package com.lp.v2.domains.auth;

import com.lp.v2.domains.auth.dto.AuthLogInReq;
import com.lp.v2.domains.auth.dto.AccessTokenRes;
import com.lp.v2.domains.auth.dto.TokenPair;
import com.lp.v2.domains.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenRes> logIn (@RequestBody AuthLogInReq req) {
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
                .body(AccessTokenRes.of(tokenPair.accessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenRes> refresh(HttpServletRequest request) {
        String refreshToken = extractRefreshTokenFromCookie(request);
        if (refreshToken == null) {
            return ResponseEntity.badRequest().build();
        }

        TokenPair tokenPair = authService.reissueTokens(refreshToken);

        // 새로운 refresh token으로 쿠키 업데이트
        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokenPair.refreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(7).getSeconds())
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(AccessTokenRes.of(tokenPair.accessToken()));
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


}
