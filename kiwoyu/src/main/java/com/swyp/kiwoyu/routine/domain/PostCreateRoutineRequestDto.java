package com.swyp.kiwoyu.routine.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostCreateRoutineRequestDto {
    private String title;
    private String memo;
    private Boolean isChecked;
    private Date routineDate;
    private long userId;
    private long mandalartId;

}
