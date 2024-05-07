package com.swyp.kiwoyu.mandalart.dto;

import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateMandalartRequestDto {
    // 중목표 수정 모달용
    private Long id;
    private String category;
    public UpdateMandalartRequestDto(Mandalart mandalart){
        this.id = mandalart.getId();
        this.category = mandalart.getCategory();
    }
}

