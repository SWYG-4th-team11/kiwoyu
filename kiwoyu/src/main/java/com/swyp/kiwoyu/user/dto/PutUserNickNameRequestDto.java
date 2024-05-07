package com.swyp.kiwoyu.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PutUserNickNameRequestDto {

    private Long userId;
    private String nickName;

    @Builder
    public PutUserNickNameRequestDto(Long userId, String nickName) {
        this.userId = userId;
        this.nickName = nickName;
    }
}

