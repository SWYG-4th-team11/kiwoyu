package com.swyp.kiwoyu.user.dto;

import com.swyp.kiwoyu.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor

public class LoginResponseDto {
    private String result;
    private String message="";
    private LoginResponse detail;


    public LoginResponseDto(String result, LoginResponse detail, String message) {
        this.result = result;
        this.detail = detail;
        this.message = message;
    }
}
