package com.swyp.kiwoyu.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class CheckUniqueNicknameRequestDto {

    private String nickName;

    @Builder
    public CheckUniqueNicknameRequestDto(String nickName) {
        this.nickName = nickName;
    }
}

