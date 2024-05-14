package com.swyp.kiwoyu.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

public class CheckNicknameDto {
    private String result;
    private String message="";
    private Boolean isUnique;


    public CheckNicknameDto(String result, Boolean isUnique, String message) {
        this.result = result;
        this.isUnique = isUnique;
        this.message = message;
    }
}
