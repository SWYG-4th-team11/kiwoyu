package com.swyp.kiwoyu.quote.repository;

import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.mandalart.repository.MandalartRepository;
import com.swyp.kiwoyu.quote.domain.Quote;
import com.swyp.kiwoyu.quote.repository.QuoteRepository;
import com.swyp.kiwoyu.quote.repository.QuoteRepositoryCustom;
import com.swyp.kiwoyu.user.domain.User;
import com.swyp.kiwoyu.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteRepositoryImpl implements QuoteRepositoryCustom {

    @Autowired
    private final EntityManager em;
    @Autowired
    UserRepository ur ;
    @Autowired
    MandalartRepository mr ;
    @Autowired
    @Lazy
    QuoteRepository rr ;

    @Autowired
    public QuoteRepositoryImpl(JpaContext context) {
        this.em = context.getEntityManagerByManagedType(Quote.class);
    }

}
