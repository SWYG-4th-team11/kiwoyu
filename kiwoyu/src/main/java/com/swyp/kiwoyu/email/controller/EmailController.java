package com.swyp.kiwoyu.email.controller;

import com.swyp.kiwoyu.email.domain.EmailMessage;
import com.swyp.kiwoyu.email.dto.EmailDto;
import com.swyp.kiwoyu.email.dto.EmailRequestDto;
import com.swyp.kiwoyu.email.service.EmailService;
import com.swyp.kiwoyu.global.util.RedisUtil;
import com.swyp.kiwoyu.user.domain.User;
import com.swyp.kiwoyu.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/send-mail")
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    private final EmailService emailService;

    private final RedisUtil redisUtil;


    // 임시 비밀번호 발급
    @PostMapping("/password")
    public ResponseEntity<?> sendPasswordMail(@RequestBody EmailRequestDto emailRequestDto) {
        if (emailRequestDto == null || emailRequestDto.getEmail() == null) {
            return ResponseEntity.badRequest().body("Invalid email request");
        }
        try{
            if (!emailService.doesUserExistByEmail(emailRequestDto.getEmail())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found with email : " + emailRequestDto.getEmail());
            }
            EmailMessage emailMessage = EmailMessage.builder()
                    .to(emailRequestDto.getEmail())
                    .subject("임시 비밀번호 발급")
                    .build();

            emailService.sendMail(emailMessage, "password");
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    // 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
    @PostMapping("/auth")
    public ResponseEntity sendJoinMail(@RequestBody EmailDto emailDto) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailDto.getEmail())
                .subject("이메일 인증을 위한 인증 코드 발송")
                .build();

        String code = emailService.sendMail(emailMessage, "email");

        EmailDto emailDto1 = new EmailDto();
        emailDto1.setEmail(emailDto.getEmail());
        emailDto1.setCode(code);

        redisUtil.setDataExpire(emailDto1.getEmail(), emailDto1.getCode(), 3);

        return ResponseEntity.ok(emailDto1);

    }

    // 인증 코드 검증
    @PostMapping("/verify")
    public ResponseEntity verifyMail(@RequestBody EmailDto emailDto) {
        boolean isVerified = emailService.verifyEmailCode(emailDto.getEmail(), emailDto.getCode());
        if (isVerified) {
            redisUtil.deleteData(emailDto.getEmail());
            return ResponseEntity.ok("인증 성공");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 실패");
        }
    }
}