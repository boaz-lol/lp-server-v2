package com.lp.v2.common.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice("com.lp.v2.domains.auth")
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 이 Advice가 적용될지 여부를 결정합니다.
     * 이미 ResponseEntity 타입으로 반환되는 경우와 String 타입은 제외합니다.
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        Class<?> actualReturnType = returnType.getParameterType();
        return !(actualReturnType.equals(ResponseEntity.class) ||
                actualReturnType.equals(String.class)); // String 타입은 예외 처리 (Swagger UI 등)
    }


    /**
     * 응답 본문을 작성하기 직전에 호출됩니다.
     * SuccessResponse인 경우 ResponseEntity로 감싸고,
     * 일반적인 객체인 경우 SuccessResponse로 감싼 후 ResponseEntity로 감쌉니다.
     */

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        // 이미 SuccessResponse인 경우 ResponseEntity로 감싸서 반환
        if (body instanceof SuccessResponse) {
            SuccessResponse<?> successResponse = (SuccessResponse<?>) body;
            HttpStatus httpStatus = HttpStatus.valueOf(successResponse.getStatus());
            return ResponseEntity.status(httpStatus).body(successResponse);
        }

        // 일반 객체인 경우 SuccessResponse로 감싼 후 ResponseEntity로 감싸서 반환
        SuccessResponse<Object> successResponse = SuccessResponse.ok(ResponseMessage.SUCCESS, body);
        response.setStatusCode(HttpStatus.valueOf(ResponseMessage.SUCCESS.getStatusCode()));
        return ResponseEntity.ok(successResponse);
    }

}