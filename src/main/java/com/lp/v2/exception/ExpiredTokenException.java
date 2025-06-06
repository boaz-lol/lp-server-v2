package com.lp.v2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 3) 토큰이 만료된 경우
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException() {
        super("토큰이 만료되었습니다.");
    }
    public ExpiredTokenException(String msg) {
        super(msg);
    }
}
