package com.swyp.kiwoyu.user.dto;

import com.swyp.kiwoyu.user.domain.User;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {

    private String nickname;
    private String email;
    private String password;

    @Builder
    public SignUpRequest(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}
