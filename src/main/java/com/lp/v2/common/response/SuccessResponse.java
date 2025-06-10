package com.lp.v2.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@RequiredArgsConstructor
public class SuccessResponse<T> {
    private final boolean success;
    private final String message;
    private final int status;
    private final Instant timestamp;
    private final T data;

    // --- 성공 응답 팩토리 메서드 ---

    // 200 OK, 데이터 포함
    public static <T> SuccessResponse<T> ok(ResponseMessage message, T data) {
        return SuccessResponse.<T>builder()
                .success(true)
                .message(message.getMessage())
                .status(message.getStatusCode())
                .timestamp(Instant.now())
                .data(data)
                .build();
    }

    // 200 OK, 데이터 없음 (예: 삭제 성공)
    public static SuccessResponse<?> ok(ResponseMessage message) {
        return SuccessResponse.builder()
                .success(true)
                .message(message.getMessage())
                .status(message.getStatusCode())
                .timestamp(Instant.now())
                .data(null) // 데이터가 없을 경우 null
                .build();
    }

    // 201 Created
    public static <T> SuccessResponse<T> created(ResponseMessage message, T data) {
        return SuccessResponse.<T>builder()
                .success(true)
                .message(message.getMessage())
                .status(message.getStatusCode())
                .timestamp(Instant.now())
                .data(data)
                .build();
    }

    public static <T> SuccessResponse<T> created(ResponseMessage message) {
        return SuccessResponse.<T>builder()
                .success(true)
                .message(message.getMessage())
                .status(message.getStatusCode())
                .timestamp(Instant.now())
                .data(null)
                .build();
    }
}
