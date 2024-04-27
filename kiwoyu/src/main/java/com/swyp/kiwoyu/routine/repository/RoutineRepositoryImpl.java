package com.swyp.kiwoyu.routine.repository;

import com.swyp.kiwoyu.routine.domain.Routine;
import com.swyp.kiwoyu.user.domain.User;
import com.swyp.kiwoyu.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

@Repository
public class RoutineRepositoryImpl implements RoutineRepositoryCustom {

    @Autowired
    private final EntityManager em;
    @Autowired
    UserRepository ur ;
    @Autowired
    @Lazy
    RoutineRepository mr ;

    @Autowired
    public RoutineRepositoryImpl(JpaContext context) {
        this.em = context.getEntityManagerByManagedType(Routine.class);
    }


    public Routine createOrUpdateRoutineWithUserId(Routine routine, Long userId) {
        User user = ur.findById(userId).orElseThrow();
        routine.setUser(user);

        return mr.save(routine);
    }
}
