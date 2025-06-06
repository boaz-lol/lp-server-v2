package com.lp.v2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("유효하지 않은 토큰입니다.");
    }
    public InvalidTokenException(String msg) {
        super(msg);
    }
}
