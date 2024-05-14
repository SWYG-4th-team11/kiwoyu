package com.swyp.kiwoyu.goal.repository;

import com.swyp.kiwoyu.goal.domain.Goal;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.user.domain.User;
import com.swyp.kiwoyu.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

@Repository
public class GoalRepositoryImpl implements GoalRepositoryCustom {

    @Autowired
    private final EntityManager em;
    @Autowired
    UserRepository ur ;
    @Autowired
    @Lazy
    GoalRepository mr ;

    @Autowired
    public GoalRepositoryImpl(JpaContext context) {
        this.em = context.getEntityManagerByManagedType(Goal.class);
    }

    public Goal createOrUpdateGoalWithUserId(Goal goal, Long userId) {
        User user = ur.findById(userId).orElseThrow();
        goal.setUser(user);

        return mr.save(goal);
    }
}
