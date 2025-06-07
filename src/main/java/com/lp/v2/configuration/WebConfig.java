package com.lp.v2.configuration;

import com.lp.v2.security.resolver.CurrentAccountIdArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // CurrentAccountIdArgumentResolver 등록
        resolvers.add(new CurrentAccountIdArgumentResolver());
    }
}
