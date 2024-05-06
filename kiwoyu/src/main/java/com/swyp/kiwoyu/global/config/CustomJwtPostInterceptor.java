package com.swyp.kiwoyu.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp.kiwoyu.jwt.JwtProvider;
import com.swyp.kiwoyu.user.dto.LoginRequest;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@PropertySource(value = {"application.properties"})
@Component
public class CustomJwtPostInterceptor implements HandlerInterceptor {

    private final JwtProvider jp = JwtProvider.getInstance();

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
//        System.out.println("----interceptor2 start");

        if(response.getStatus() != 200){
            return;
        }
        RequestWrapper rw = new RequestWrapper(request);
        ObjectMapper om = new ObjectMapper();
        LoginRequest lr = om.readValue(rw.getBody(), LoginRequest.class);

        String jwt = jp.createToken( lr.getEmail());
//        response.setHeader("Set-Cookie","token=" + jwt +". ;Path=/; Domain=localhost; Max-Age=604800; Same-Site=lax;HttpOnly;");
//        response.addCookie(new Cookie("token",jwt));
//        response.setHeader("token",jwt);
//        System.out.println(jwt);
//        System.out.println("----interceptor2 done");

    }

}
