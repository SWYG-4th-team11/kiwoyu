package com.swyp.kiwoyu.quote.service;

import com.swyp.kiwoyu.quote.domain.Quote;
import com.swyp.kiwoyu.quote.repository.QuoteRepository;
import com.swyp.kiwoyu.quote.repository.QuoteRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.round;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;
    private QuoteRepositoryImpl quoteRepositoryImpl;

    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    public Optional<Quote> getQuoteById(Long id) {
        return quoteRepository.findById(id);
    }
    public Optional<Quote> getRandomQuote() {
        double d = Math.random();
        long x = round(d * 100);
        return quoteRepository.findById(x);
    }

    public Quote createOrUpdateQuote(Quote quote) {
        return quoteRepository.save(quote);
    }


    public void deleteQuote(Long id) {
        quoteRepository.deleteById(id);
    }
}