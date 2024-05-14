package com.swyp.kiwoyu.mandalart.repository;
import com.swyp.kiwoyu.goal.domain.Goal;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.routine.domain.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MandalartRepository extends JpaRepository<Mandalart, Long>, MandalartRepositoryCustom{
    @Query("select m from Mandalart m where m.user.id = :userId")
    List<Mandalart> findByUserId(@Param("userId") Long userId);

    @Query("select g from Mandalart m join Goal g on m.id = :mandalartId and m.id = g.mandalart.id")
    List<Goal> findMandalartGoalById(@Param("mandalartId") Long mandalartId);


}