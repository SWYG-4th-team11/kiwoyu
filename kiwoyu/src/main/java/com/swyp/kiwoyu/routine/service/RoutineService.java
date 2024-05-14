package com.swyp.kiwoyu.routine.service;

import com.swyp.kiwoyu.global.util.ExpProcess;
import com.swyp.kiwoyu.global.util.dto.ExpLevelDto;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.mandalart.repository.MandalartRepository;
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
    @Autowired
    private MandalartRepository mandalartRepository;
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

    public Routine toggleIsChecked(Routine routine) {
        Boolean isChecked = routine.getIsChecked();
        routine.setIsChecked(!isChecked);
        if (isChecked == false && routine.getIsAppliedToExp() ==false) {
            Mandalart m = routine.getMandalart();
            ExpLevelDto expLevelDto = ExpProcess.updateExp(m,"routine");

            m.setExp(expLevelDto.getExp());
            m.setLevel(expLevelDto.getLevel());
            m.setLevelUp(expLevelDto.getLevelUp());
            mandalartRepository.save(m);

            routine.setIsAppliedToExp(true);
        }
        routineRepository.save(routine);
        return createOrUpdateRoutine(routine);
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