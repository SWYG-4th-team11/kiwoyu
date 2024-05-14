package com.swyp.kiwoyu.goal.service;

import com.swyp.kiwoyu.global.util.ExpProcess;
import com.swyp.kiwoyu.global.util.dto.ExpLevelDto;
import com.swyp.kiwoyu.goal.domain.Goal;
import com.swyp.kiwoyu.goal.dto.UpdateGoalRequestDto;
import com.swyp.kiwoyu.goal.dto.UpdateGoalResponseDto;
import com.swyp.kiwoyu.goal.repository.GoalRepository;
import com.swyp.kiwoyu.goal.repository.GoalRepositoryImpl;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.mandalart.repository.MandalartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private MandalartRepository mandalartRepository;
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

    public UpdateGoalResponseDto updateGoal(UpdateGoalRequestDto dto) {
        Optional<Goal> existingGoal = getGoalById(dto.getId());
        if (existingGoal.isPresent()) {
            Goal updateGoal = new Goal(existingGoal.get(), dto);

            if(dto.isAchieved()==true && updateGoal.getIsAppliedToExp()==false){
                ExpLevelDto expLevelDto = ExpProcess.updateExp(updateGoal.getMandalart(),updateGoal.getType());
                Mandalart m = updateGoal.getMandalart();
                m.setExp(expLevelDto.getExp());
                m.setLevel(expLevelDto.getLevel());
                m.setLevelUp(expLevelDto.getLevelUp());
                mandalartRepository.save(m);

                updateGoal.setIsAppliedToExp(true);
            }


            Goal updatedGoal = goalRepository.save(updateGoal);
            return new UpdateGoalResponseDto(updatedGoal);
        } else {
            return null;
        }
    }

    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }
}