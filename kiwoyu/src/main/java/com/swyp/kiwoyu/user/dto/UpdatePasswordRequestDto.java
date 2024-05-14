package com.swyp.kiwoyu.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordRequestDto {

    private Long id;

    private String password;

    private String newPassword;

    @Builder
    public UpdatePasswordRequestDto(Long id, String password, String newPassword) {
        this.id = id;
        this.password = password;
        this.newPassword = newPassword;
    }
}

