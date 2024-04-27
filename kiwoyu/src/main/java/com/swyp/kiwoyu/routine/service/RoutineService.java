package com.swyp.kiwoyu.routine.service;

import com.swyp.kiwoyu.routine.domain.Routine;
import com.swyp.kiwoyu.routine.repository.RoutineRepository;
import com.swyp.kiwoyu.routine.repository.RoutineRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Routine createOrUpdateRoutineWithUserId(Routine routine, Long userId) {
        return routineRepository.createOrUpdateRoutineWithUserId(routine,userId);
    }
        public void deleteRoutine(Long id) {
        routineRepository.deleteById(id);
    }
}