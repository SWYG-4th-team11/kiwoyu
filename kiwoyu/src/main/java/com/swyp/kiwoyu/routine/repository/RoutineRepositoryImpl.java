package com.swyp.kiwoyu.routine.repository;

import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.mandalart.repository.MandalartRepository;
import com.swyp.kiwoyu.routine.domain.Routine;
import com.swyp.kiwoyu.user.domain.User;
import com.swyp.kiwoyu.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class RoutineRepositoryImpl implements RoutineRepositoryCustom {

    @Autowired
    private final EntityManager em;
    @Autowired
    UserRepository ur ;
    @Autowired
    MandalartRepository mr ;
    @Autowired
    @Lazy
    RoutineRepository rr ;

    @Autowired
    public RoutineRepositoryImpl(JpaContext context) {
        this.em = context.getEntityManagerByManagedType(Routine.class);
    }

    @Override
    public Routine upsertRoutineWithUserAndMandalart(Routine routine, Long userId, Long mandalartId) {
        User user = ur.findById(userId).orElseThrow();
        Mandalart mandalart = mr.findById(mandalartId).orElseThrow();

        routine.setUser(user);
        routine.setMandalart(mandalart);
        return rr.save(routine);
    }

}
