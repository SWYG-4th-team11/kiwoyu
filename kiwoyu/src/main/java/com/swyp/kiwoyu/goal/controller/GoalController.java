package com.swyp.kiwoyu.goal.controller;

import com.swyp.kiwoyu.goal.domain.Goal;
import com.swyp.kiwoyu.goal.dto.UpdateGoalRequestDto;
import com.swyp.kiwoyu.goal.dto.UpdateGoalResponseDto;
import com.swyp.kiwoyu.goal.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
        Goal createdGoal = goalService.createOrUpdateGoal(goal);
        return new ResponseEntity<>(createdGoal, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateGoalResponseDto> updateGoalAsDto(@RequestBody UpdateGoalRequestDto dto) {
        UpdateGoalResponseDto res = goalService.updateGoal(dto);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable("id") Long id) {
        Optional<Goal> goal = goalService.getGoalById(id);
        if (goal.isPresent()) {
            goalService.deleteGoal(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}