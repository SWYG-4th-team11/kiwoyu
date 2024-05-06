package com.swyp.kiwoyu.global.config;

import com.swyp.kiwoyu.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationNotSupportedException;
import java.util.logging.Logger;

@PropertySource(value = {"application.properties"})
@Component
public class CustomJwtInterceptor implements HandlerInterceptor {

    private String adminToken = "";
    private final JwtProvider jp = JwtProvider.getInstance();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        System.out.println("----interceptor start");
//        System.out.println(jp.createToken("admin"));
        /* Check Token */
        String raw = jp.resolveToken(request);
//        System.out.println(raw);//FIXME:: delete

        if (raw==null || raw.length() == 0){
            System.out.println("----Error: token is not found.");
            response.setStatus(403);
            return false;
        } else {
            if( raw.endsWith(adminToken) ){
                //FIXME::: Free-pass token. This logic should be removed before deploy to prod.
                return true;
            }
            if(!jp.validateToken(raw)){
                System.out.println("----Error: token invalid.");
                response.setStatus(403);
                return false;
            }
            Claims k = jp.parseToken(raw);
//            System.out.println(k.entrySet());
        }
        return true;
    }
}
