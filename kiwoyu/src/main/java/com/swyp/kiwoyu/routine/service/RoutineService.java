package com.swyp.kiwoyu.routine.service;

import com.swyp.kiwoyu.routine.domain.PostCreateRoutineRequestDto;
import com.swyp.kiwoyu.routine.domain.Routine;
import com.swyp.kiwoyu.routine.domain.RoutineDto;
import com.swyp.kiwoyu.routine.repository.RoutineRepository;
import com.swyp.kiwoyu.routine.repository.RoutineRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;
    private RoutineRepositoryImpl routineRepositoryImpl;

    public List<Routine> getAllRoutines() {
        return routineRepository.findAll();
    }

    public Optional<Routine> getRoutineById(Long id) {
        return routineRepository.findById(id);
    }

    public Routine createOrUpdateRoutine(Routine routine) {
        return routineRepository.save(routine);
    }

    public Routine upsertRoutineWithUserAndMandalart(PostCreateRoutineRequestDto dto) {
        Routine newRoutine = new Routine();
        newRoutine.setMemo(dto.getMemo());
        newRoutine.setRoutine_date(dto.getRoutineDate());
        newRoutine.setTitle(dto.getTitle());
        newRoutine.setIsChecked(dto.getIsChecked());
        return routineRepository.upsertRoutineWithUserAndMandalart(newRoutine,dto.getUserId(), dto.getMandalartId());
    }
    public List<RoutineDto> getRoutinesByUserMandalartAndDate(Long userId, Long mandalartId, Date routineDate){
        System.out.println("getRoutinesByUserMandalartAndDate--start");

        List<Routine> routines =  routineRepository.findByUserMandalartAndDate(userId, mandalartId, routineDate);
        List<RoutineDto> res;
        res = routines.stream().map(
                (it)->{
                    return new RoutineDto(it);
        }).collect(Collectors.toList());
        return res;
    }


    public void deleteRoutine(Long id) {
        routineRepository.deleteById(id);
    }
}