package com.swyp.kiwoyu.routine.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostRoutineByIdAndDateResponseDto {
    private List<RoutineDto> routines;
}
