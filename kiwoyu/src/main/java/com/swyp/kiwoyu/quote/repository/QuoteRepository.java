package com.swyp.kiwoyu.quote.repository;
import com.swyp.kiwoyu.quote.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long>, QuoteRepositoryCustom {
}