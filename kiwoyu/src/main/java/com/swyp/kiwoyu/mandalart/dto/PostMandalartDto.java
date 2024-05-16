package com.swyp.kiwoyu.mandalart.dto;

import com.swyp.kiwoyu.global.util.DateProcess;
import com.swyp.kiwoyu.goal.dto.GoalDto;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PostMandalartDto {

    /* Will Be Initialized */
//    private Long id; //mandalart id
//    private Integer level;
//    private Integer exp;


    private String title; // same as main goal content
    private String memo;
    private List<String> categories; //
    private Long userId;
    private Date due;

//    private GoalDto mainGoal;
}

