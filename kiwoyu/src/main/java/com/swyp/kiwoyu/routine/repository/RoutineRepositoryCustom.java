package com.swyp.kiwoyu.routine.repository;
import com.swyp.kiwoyu.routine.domain.Routine;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineRepositoryCustom {
    Routine upsertRoutineWithUserAndMandalart(Routine routine, Long userId, Long mandalartId);
//    Optional<List<Routine>> getRoutinesByUserMandalartAndDate(Long userId, Long mandalartId, Date routineDate);

}