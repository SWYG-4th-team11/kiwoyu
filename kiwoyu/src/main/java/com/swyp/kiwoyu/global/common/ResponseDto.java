package com.swyp.kiwoyu.global.common;

import com.swyp.kiwoyu.user.dto.LoginResponse;

public class ResponseDto {
    private String result;
    private String message="";
    private Object detail;

    public ResponseDto(String result, Object detail, String message) {
        this.result = result;
        this.detail = detail;
        this.message = message;
    }
}
