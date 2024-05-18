package com.swyp.kiwoyu.user.dto;

import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class MyPageInfoDto {
    private Long id;
    private String email;
    private String nickname;
    private Boolean isDeleted;
    private Date createdAt;
    private Date updatedAt;

    private Integer level=1;
    private Integer exp=0;

    public MyPageInfoDto(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.isDeleted = user.getIsDeleted();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
    public MyPageInfoDto(User user, Mandalart mandalart){
        this(user);
        this.exp = mandalart.getExp();
        this.level = mandalart.getLevel();
    }
}
