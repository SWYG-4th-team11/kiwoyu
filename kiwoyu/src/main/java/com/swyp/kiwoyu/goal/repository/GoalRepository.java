package com.swyp.kiwoyu.goal.repository;
import com.swyp.kiwoyu.goal.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long>, GoalRepositoryCustom {
}