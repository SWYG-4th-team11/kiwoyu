package com.swyp.kiwoyu.global.config;

import com.swyp.kiwoyu.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final CustomAuthenticationEntrypoint entrypoint= new CustomAuthenticationEntrypoint();
    private final CustomAuthenticationManager authManager= new CustomAuthenticationManager();

    private final List allowedOrigins = List.of(
            "http://localhost:3000"
    );
    private final List allowedPatterns = List.of(
            "http://*.swygbro.com",
            "https://*.swygbro.com",
            "http://localhost:3000"
    );

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // JWT 인증 필터 적용
//        http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider), BasicAuthenticationFilter.class);

        http.authenticationManager(authManager);

        // base 64
        http.httpBasic(c->c.disable());
        // CSRF 설정: 이걸 지우면 403에러 발생
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 인증 예외 처리
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .anyRequest()
                        .permitAll()
        );

        // CORS 설정
        http.cors(
                c -> {
                            CorsConfigurationSource source = request -> {
                                // Cors 허용 패턴
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowCredentials(Boolean.TRUE);
                                config.setAllowedHeaders(List.of("*"));
                                config.setAllowedMethods(List.of("*"));
                                config.setAllowedOrigins(allowedOrigins);
                                config.setAllowedOriginPatterns(allowedPatterns);
                                config.setAllowCredentials(true);
                                return config;
                            };
                            c.configurationSource(source);
                        }
                );

        // token 설정

        // 에러 핸들링
        http.exceptionHandling(it->{ it.authenticationEntryPoint(entrypoint);});

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
