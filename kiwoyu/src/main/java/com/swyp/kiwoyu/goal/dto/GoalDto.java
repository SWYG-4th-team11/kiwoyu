package com.swyp.kiwoyu.goal.dto;

import com.swyp.kiwoyu.goal.domain.Goal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class GoalDto {
    /* Goal 일괄 조회용 */
    private Long id;
    private String title;
    private String type;
    private String content;

    private Date goalDate;
    private boolean isAchieved;

    private Long parentGoalId;
    private List<GoalDto> subGoals = new ArrayList<>();

    public GoalDto(Goal goal){
        this.id = goal.getId();
        this.title = goal.getTitle();
        this.type = goal.getType();
        this.content = goal.getContent();
        this.goalDate = goal.getGoalDate();
        this.isAchieved = goal.getIsAchieved();
        this.parentGoalId = goal.getParentGoalId();

    }
    public GoalDto(Goal goal, List<GoalDto> subGoals){
        this(goal);
        this.subGoals = subGoals;
    }

    @Override
    public String toString() {
        return "id:"+this.id+" / title:"+this.title+" / type:"+this.type+" / content:"+this.content+" / goalDate:"+goalDate+" / isAchieved:"+isAchieved;
    }
}

