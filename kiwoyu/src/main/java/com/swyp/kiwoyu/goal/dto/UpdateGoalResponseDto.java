package com.swyp.kiwoyu.goal.dto;

import com.swyp.kiwoyu.goal.domain.Goal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class UpdateGoalResponseDto {

    private Long id;
    private String type;
    private String title;
    private String content;
    private Long parentGoalId;

    private Long userId;
    private Long mandalartId;

    @Builder
    public UpdateGoalResponseDto(Goal goal) {
        this.id = goal.getId();
        this.type = goal.getType();
        this.title = goal.getTitle();
        this.content = goal.getContent();
        this.parentGoalId = goal.getParentGoalId();

        this.userId = goal.getUser().getId();
        this.mandalartId = goal.getMandalart().getId();

    }
}

