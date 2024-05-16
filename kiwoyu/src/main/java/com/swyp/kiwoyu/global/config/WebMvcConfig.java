package com.swyp.kiwoyu.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomJwtInterceptor())
                /* 계정 없는 상태에서 사용 가능한 토큰 */
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/signup")
                .excludePathPatterns("/api/user/logout")
                .excludePathPatterns("/api/user/nickname/check-unique")
                .excludePathPatterns("/api/send-mail/password")
                .excludePathPatterns("/api/send-mail/auth")
                .excludePathPatterns("/api/send-mail/verify")
                .excludePathPatterns("/swagget-ui/*")
                .excludePathPatterns("/v3/*");

        registry.addInterceptor(new CustomJwtPostInterceptor())
                .addPathPatterns("/api/user/login");

    }
}
