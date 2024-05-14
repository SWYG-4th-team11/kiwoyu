package com.swyp.kiwoyu.global.util.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ExpLevelDto {
    private Integer exp;
    private Integer level;
    private Boolean levelUp;
    public ExpLevelDto(Integer exp, Integer level, Boolean levelUp) {
        this.exp = exp;
        this.level = level;
        this.levelUp = levelUp;
    }
}
