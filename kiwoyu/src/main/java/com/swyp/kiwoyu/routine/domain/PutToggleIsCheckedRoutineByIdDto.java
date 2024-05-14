package com.swyp.kiwoyu.routine.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PutToggleIsCheckedRoutineByIdDto {
    private Long routineId;
    private Boolean isChecked;

    public PutToggleIsCheckedRoutineByIdDto(Routine routine){
        this.routineId = routine.getId();
        this.isChecked = routine.getIsChecked();
    }
}
