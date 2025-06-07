package com.lp.v2.exception;

import com.lp.v2.common.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> handleIllegalArgument(
            IllegalArgumentException ex, WebRequest request) {
        BaseResponse response = BaseResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .timestamp(Instant.now().toString())
                .status(HttpStatus.CONFLICT.value())
                .build();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler({
            UnauthenticatedException.class,
            InvalidTokenException.class,
            ExpiredTokenException.class
    })
    public ResponseEntity<BaseResponse> handleAuthExceptions(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(BaseResponse.fail(ex.getMessage()));
    }

}
