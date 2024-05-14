package com.swyp.kiwoyu.routine.domain;

import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class RoutineDto {
    private Long id;
    private String title;
    private String memo;
    private Boolean isChecked;
    private Date routineDate;

    private Long userId;

    private Long mandalartId;

    public RoutineDto(Routine routine){
        this.id = routine.getId();
        this.title = routine.getTitle();
        this.memo = routine.getMemo();
        this.isChecked = routine.getIsChecked();
        this.routineDate = routine.getRoutine_date();

        this.userId = routine.getUser().getId();
        this.mandalartId = routine.getMandalart().getId();

    }
}
