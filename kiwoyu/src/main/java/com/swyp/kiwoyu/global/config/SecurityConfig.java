package com.swyp.kiwoyu.global.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(CsrfConfigurer<HttpSecurity>::disable)
//                .authorizeHttpRequests(requests ->
//                        requests.requestMatchers("/", "/swagger-ui/**").permitAll()	// requestMatchers의 인자로 전달된 url은 모두에게 허용
//                                .anyRequest().authenticated()	// 그 외의 모든 요청은 인증 필요
//                )
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .build();
//    }
}