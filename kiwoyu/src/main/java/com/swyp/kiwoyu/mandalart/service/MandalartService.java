package com.swyp.kiwoyu.mandalart.service;
import com.swyp.kiwoyu.goal.domain.Goal;
import com.swyp.kiwoyu.goal.dto.GoalDto;
import com.swyp.kiwoyu.goal.dto.UpdateGoalResponseDto;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.mandalart.dto.GetMandalartDto;
import com.swyp.kiwoyu.mandalart.dto.UpdateMandalartRequestDto;
import com.swyp.kiwoyu.mandalart.repository.MandalartRepository;
import com.swyp.kiwoyu.mandalart.repository.MandalartRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MandalartService {

    @Autowired
    private MandalartRepository mandalartRepository;
    private MandalartRepositoryImpl mandalartRepositoryImpl;

    public List<Mandalart> getAllMandalarts() {
        return mandalartRepository.findAll();
    }

    public Optional<Mandalart> getMandalartById(Long id) {
        return mandalartRepository.findById(id);
    }

    public Mandalart createOrUpdateMandalart(Mandalart mandalart) {
        return mandalartRepository.save(mandalart);
    }

    public Mandalart createOrUpdateMandalartWithUserId(Mandalart mandalart, Long userId) {
        return mandalartRepository.createOrUpdateMandalartWithUserId(mandalart,userId);
    }
    public void deleteMandalart(Long id) {
        mandalartRepository.deleteById(id);
    }

    public UpdateMandalartRequestDto updateMandalartCategory(UpdateMandalartRequestDto dto){
        Optional<Mandalart> existingMandalart = getMandalartById(dto.getId());
        if (existingMandalart.isPresent()) {
            Mandalart m = existingMandalart.get();
            m.setCategory(dto.getCategory());

            return new UpdateMandalartRequestDto(mandalartRepository.save(m));
        } else {
            return null;
        }
    }

    public List<GetMandalartDto> getMandalartWholeInfo(Long userId){
        List<GetMandalartDto> res = new ArrayList<>();

        List<Mandalart> ms = mandalartRepository.findByUserId(userId);

        for( Mandalart m: ms){
            GetMandalartDto dto= new GetMandalartDto(m);
            List<Goal> goals = mandalartRepository.findMandalartGoalById(m.getId());
            System.out.println(goals);
            List<GoalDto> middleGoals=new ArrayList<>();
            Map<Long,Integer> id2Idx = new HashMap<>();
            goals.stream().filter(it-> "middle".contentEquals(it.getType())).forEach(
                    it -> {
                        middleGoals.add(new GoalDto(it));
                        id2Idx.put(it.getId(),middleGoals.size()-1);
                    }
            );
            System.out.println(middleGoals);
            System.out.println(id2Idx);
            for(Goal g : goals){
                if("middle".contentEquals(g.getType())){
                    System.out.println(g.getId()+","+g.getType());
                } else if("main".contentEquals(g.getType())){
                    System.out.println(g.getId()+","+g.getType());
                    dto = new GetMandalartDto(m,new GoalDto(g));
//                    dto.setMainGoal(new GoalDto(g));
                } else{
                    System.out.println(g.getId()+","+g.getType()+","+g.getParentGoalId());
                    // Arrange Small Goals
                    GoalDto parentMiddleGoal = middleGoals.get(id2Idx.get(g.getParentGoalId()));
                    List<GoalDto> subGoals = parentMiddleGoal.getSubGoals();
                    subGoals.add(new GoalDto(g));
                    parentMiddleGoal.setSubGoals(subGoals);
                }
            }
            dto.setSubGoals(middleGoals);
            res.add(dto);
        }

        return res;
    }
}