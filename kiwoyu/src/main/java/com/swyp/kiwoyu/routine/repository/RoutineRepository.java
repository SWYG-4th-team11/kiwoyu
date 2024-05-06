package com.swyp.kiwoyu.routine.repository;
import com.swyp.kiwoyu.routine.domain.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long>, RoutineRepositoryCustom {

    @Query("select r from Routine r where r.user.id = :userId and r.mandalart.id = :mandalartId and r.routine_date = :routineDate")
    List<Routine> findByUserMandalartAndDate(@Param("userId") Long userId, @Param("mandalartId") Long mandalartId , @Param("routineDate") Date routineDate);

}