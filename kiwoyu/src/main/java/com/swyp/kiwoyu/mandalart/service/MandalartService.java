package com.swyp.kiwoyu.mandalart.service;
import com.swyp.kiwoyu.goal.domain.Goal;
import com.swyp.kiwoyu.goal.dto.GoalDto;
import com.swyp.kiwoyu.goal.dto.UpdateGoalResponseDto;
import com.swyp.kiwoyu.goal.repository.GoalRepository;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.mandalart.dto.GetMandalartDto;
import com.swyp.kiwoyu.mandalart.dto.PostMandalartDto;
import com.swyp.kiwoyu.mandalart.dto.UpdateMandalartRequestDto;
import com.swyp.kiwoyu.mandalart.repository.MandalartRepository;
import com.swyp.kiwoyu.mandalart.repository.MandalartRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MandalartService {
    private final int _GOAL_COUNT = 4;
    @Autowired
    private MandalartRepository mandalartRepository;

    @Autowired
    private GoalRepository goalRepository;
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
    public GetMandalartDto createOrUpdateMandalartWithUserId(PostMandalartDto dto) {
        Mandalart m = new Mandalart(dto);
        Long userId = dto.getUserId();
        Mandalart res = mandalartRepository.createOrUpdateMandalartWithUserId(m,userId);

        Goal g = new Goal(dto,res);
        Goal createdMainGoal = goalRepository.createOrUpdateGoalWithUserId(g,userId);

        /* init. subGoals */
        List<GoalDto> childMiddleGoals = new ArrayList<>();

        for(int i = 0 ; i < _GOAL_COUNT; ++i){
            Goal middleGoal = new Goal(dto,g.getId(),res,"middle");
            Goal createdMiddleGoal = goalRepository.save(middleGoal);
            childMiddleGoals.add(new GoalDto(createdMiddleGoal));

            List<GoalDto> childSmallGoals = new ArrayList<>();
            for(int j = 0 ; j < _GOAL_COUNT; ++j) {
                Goal smallGoal = new Goal(dto,createdMiddleGoal.getId(),res,"small");
                Goal createdSmallGoal = goalRepository.save(smallGoal);
                childSmallGoals.add(new GoalDto(createdSmallGoal));
            }
            childMiddleGoals.get(childMiddleGoals.size()-1).setSubGoals(childSmallGoals);
        }

        GetMandalartDto responseDto = new GetMandalartDto(res,new GoalDto(g));
        responseDto.setMainGoal(new GoalDto(createdMainGoal));
        responseDto.setSubGoals(childMiddleGoals);
        return responseDto;
    }
    public void deleteMandalart(Long id) {
        mandalartRepository.deleteById(id);
    }

    public UpdateMandalartRequestDto updateMandalartCategory(UpdateMandalartRequestDto dto){
        Optional<Mandalart> existingMandalart = getMandalartById(dto.getId());
        if (existingMandalart.isPresent()) {
            Mandalart m = existingMandalart.get();
            m.setCategory(dto.getCategory().toString().replace("[","").replace("]",""));
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
        /* LevelUp Only Once */

        return res;
    }

    public void initializeLevelup(List<GetMandalartDto> mandalarts) {

        for( GetMandalartDto dto: mandalarts){
            if (true == dto.getLevelUp()){
                Mandalart m = mandalartRepository.findById(dto.getId()).orElse(null);
                if(m!=null){
                    m.setLevelUp(false);
                    mandalartRepository.save(m);
                }
            }
        }

    }
}