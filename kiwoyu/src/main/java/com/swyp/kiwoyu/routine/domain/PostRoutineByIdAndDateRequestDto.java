package com.swyp.kiwoyu.routine.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostRoutineByIdAndDateRequestDto {
    private long userId;
    private long mandalartId;
    private Date routineDate;

}
