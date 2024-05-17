package com.swyp.kiwoyu.goal.repository;
import com.swyp.kiwoyu.goal.domain.Goal;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long>, GoalRepositoryCustom {
    List<Goal> findAllByMandalart(Mandalart mandalart);

}