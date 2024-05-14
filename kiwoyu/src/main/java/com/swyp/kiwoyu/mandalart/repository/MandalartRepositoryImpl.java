package com.swyp.kiwoyu.mandalart.repository;

import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.user.domain.User;
import com.swyp.kiwoyu.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

@Repository
public class MandalartRepositoryImpl implements MandalartRepositoryCustom {

    @Autowired
    private final EntityManager em;
    @Autowired
    UserRepository ur ;
    @Autowired
    @Lazy
    MandalartRepository mr ;

    @Autowired
    public MandalartRepositoryImpl(JpaContext context) {
        this.em = context.getEntityManagerByManagedType(Mandalart.class);
    }


    public Mandalart createOrUpdateMandalartWithUserId(Mandalart mandalart, Long userId) {
        User user = ur.findById(userId).orElseThrow();
        mandalart.setUser(user);

        return mr.save(mandalart);
    }

}
