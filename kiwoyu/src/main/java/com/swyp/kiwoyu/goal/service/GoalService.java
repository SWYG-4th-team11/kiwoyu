package com.swyp.kiwoyu.goal.service;

import com.swyp.kiwoyu.goal.domain.Goal;
import com.swyp.kiwoyu.goal.repository.GoalRepository;
import com.swyp.kiwoyu.goal.repository.GoalRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;
    private GoalRepositoryImpl goalRepositoryImpl;

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public Optional<Goal> getGoalById(Long id) {
        return goalRepository.findById(id);
    }

    public Goal createOrUpdateGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }
}