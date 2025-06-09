package com.lp.v2.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final int status;
    private final Instant timestamp;
    private final T data;        // 추가
}
