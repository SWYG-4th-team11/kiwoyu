package com.swyp.kiwoyu.routine.repository;
import com.swyp.kiwoyu.routine.domain.Routine;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineRepositoryCustom {
    Routine createOrUpdateRoutineWithUserId(Routine routine, Long userId);
}