package com.lp.v2.security.resolver;

import com.lp.v2.exception.InvalidTokenException;
import com.lp.v2.exception.UnauthenticatedException;
import com.lp.v2.security.annotation.CurrentAccountId;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CurrentAccountIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentAccountId.class)
                && Long.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Long resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new UnauthenticatedException();
        }
        if (!auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            throw new UnauthenticatedException();
        }

        // name 에 subject(accountId)를 넣었다 가정
        String subject = auth.getName();
        try {
            return Long.valueOf(subject);
        } catch (NumberFormatException ex) {
            throw new InvalidTokenException("토큰 subject 형식 오류");
        }
    }
}