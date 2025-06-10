package com.lp.v2.common.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.Instant;

@ControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().equals(SuccessResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ProblemDetail) {
            return body;
        }

        int actualStatus;
        if (response instanceof ServletServerHttpResponse servlet) {
            actualStatus = servlet.getServletResponse().getStatus();
        } else {
            actualStatus = ResponseMessage.SUCCESS.getStatusCode();
        }

        return SuccessResponse.<Object>builder()
                .success(true)
                .message(ResponseMessage.SUCCESS.getMessage())
                .status(actualStatus)
                .timestamp(Instant.now())
                .data(body)
                .build();
    }
}