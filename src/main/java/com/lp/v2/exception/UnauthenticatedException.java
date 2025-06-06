package com.lp.v2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 1) 인증 정보가 없는 경우
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthenticatedException extends RuntimeException {
    public UnauthenticatedException() {
        super("인증 정보가 없습니다.");
    }
    public UnauthenticatedException(String msg) {
        super(msg);
    }
}

