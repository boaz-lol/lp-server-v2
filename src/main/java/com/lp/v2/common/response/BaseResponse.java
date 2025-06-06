package com.lp.v2.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@RequiredArgsConstructor
public class BaseResponse {
    private final boolean success;
    private final String message;
    private final String timestamp;
    private final int status;

    public static BaseResponse success(ResponseMessage responseMessage) {
        return BaseResponse.builder()
                .success(true)
                .message(responseMessage.getMessage())
                .timestamp(Instant.now().toString())
                .status(responseMessage.getStatusCode())
                .build();
    }

    public static BaseResponse error(ResponseMessage responseMessage) {
        return BaseResponse.builder()
                .success(false)
                .message(responseMessage.getMessage())
                .timestamp(Instant.now().toString())
                .status(responseMessage.getStatusCode())
                .build();
    }
}