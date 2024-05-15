package com.swyp.kiwoyu.user.dto;

import com.swyp.kiwoyu.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

public class SignUpResponseDto {
    private String result;
    private String message="";
    private SignUpRequest detail;


    public SignUpResponseDto(String result, SignUpRequest detail, String message) {
        this.result = result;
        this.detail = detail;
        this.message = message;

    }


}
