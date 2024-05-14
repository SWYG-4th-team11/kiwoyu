package com.swyp.kiwoyu.mandalart.dto;

import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateMandalartRequestDto {
    // 중목표 수정 모달용
    private Long id;
    private List<String> category;
    public UpdateMandalartRequestDto(Mandalart mandalart){
        this.id = mandalart.getId();
        this.category = Arrays.asList(mandalart.getCategory().split(", "));
    }
}

