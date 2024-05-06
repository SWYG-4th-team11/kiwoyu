package com.swyp.kiwoyu.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;


@PropertySource(value = {"application.properties"})
@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String secretString;

    private Key secretKey;
    private static JwtProvider INSTANCE;

    private JwtProvider() {

    }

    public static JwtProvider getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new JwtProvider();
        }
        return INSTANCE;
    }
    // 만료시간 : 1Hour
    private final long exp = 1000L * 60 * 60;


    @PostConstruct
    protected void init() {
        JwtProvider.getInstance().setSecretKey(secretString);
//        System.out.println(secretString);
//        this.secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
//        System.out.println(secretKey);
//        this.setSecretKey(secretString);

    }
    private void setSecretKey(String secretString){
        this.secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }
    // 토큰 생성
    public String createToken(String account) {
        Claims claims = Jwts.claims().setSubject(account);
//        claims.put("roles", new ArrayList<>());
        Date now = new Date();
//        System.out.println(secretKey);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + exp))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }


    // 토큰에 담겨있는 유저 account 획득
    public String getAccount(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Authorization Header를 통해 인증을 한다.
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            // Bearer 검증
//            System.out.println("----ValidateToken");
//            System.out.println(this.createToken("admin"));
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                System.out.println("----Bearer is missing");
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }
//            System.out.println(secretKey);
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            System.out.println("----" + e.getMessage());
            return false;
        }
    }
    public Claims parseToken(String token) {
        try {
            if(token.startsWith("Bearer ")){
                token = token.split(" ")[1].trim();
            }
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return claims.getBody();
        } catch (Exception e) {
            return null;
        }
    }
}