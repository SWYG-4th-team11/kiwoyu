package com.swyp.kiwoyu.mandalart.dto;

import com.swyp.kiwoyu.global.util.DateProcess;
import com.swyp.kiwoyu.goal.dto.GoalDto;
import com.swyp.kiwoyu.goal.dto.UpdateGoalRequestDto;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.type.descriptor.DateTimeUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class GetMandalartDto {

    private Long id; //mandalart id
    private String title; // same as main goal content
    private List<String> categories; //

    private Long userId;

    private Integer level;
    private Integer exp;

    private Date due;
    private Integer dDay;
    private Boolean levelUp;

    private GoalDto mainGoal;
    private List<GoalDto> subGoals;

    public GetMandalartDto(Mandalart mandalart){
        this.id = mandalart.getId();
        this.title = mandalart.getTitle();
        this.categories = Arrays.asList(mandalart.getCategory().split(", "));
        this.userId = mandalart.getUser().getId();
        this.level = mandalart.getLevel();
        this.exp = mandalart.getExp();
        this.levelUp = mandalart.getLevelUp();
    }
    public GetMandalartDto(Mandalart mandalart, GoalDto mainGoal) {
        this(mandalart);

        this.mainGoal = mainGoal;
        this.subGoals = mainGoal.getSubGoals();
        this.title= mainGoal.getTitle();
        this.due = mainGoal.getGoalDate();
        this.dDay = DateProcess.calculateDday(mainGoal.getGoalDate());
    }
}

