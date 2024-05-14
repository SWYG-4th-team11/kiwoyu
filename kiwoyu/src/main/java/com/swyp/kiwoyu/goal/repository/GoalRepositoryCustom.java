package com.swyp.kiwoyu.goal.repository;
import com.swyp.kiwoyu.goal.domain.Goal;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepositoryCustom {
    public Goal createOrUpdateGoalWithUserId(Goal goal, Long userId);
}