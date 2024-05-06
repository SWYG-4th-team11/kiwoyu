package com.swyp.kiwoyu.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor

public class LoginResponse {
    private Long id;
    private String email;
    private String nickname;
    private String token;
    private Date createdDateTime;

    public LoginResponse(Long id, String email, String nickname, String token, Date createdDateTime) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.token = token;
        this.createdDateTime = createdDateTime;
    }
}
