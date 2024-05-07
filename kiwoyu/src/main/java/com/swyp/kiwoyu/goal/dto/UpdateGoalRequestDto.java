package com.swyp.kiwoyu.goal.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateGoalRequestDto {
    // 중목표 수정 모달용
    private Long id;
    private String title;
    private String content;
    private Date goalDate;
    private boolean isAchieved;

}

