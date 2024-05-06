package com.swyp.kiwoyu.quote.controller;

import com.swyp.kiwoyu.quote.domain.Quote;
import com.swyp.kiwoyu.quote.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quote")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    @GetMapping
    public ResponseEntity<Quote> getQuotes() {
        Quote quote = quoteService.getRandomQuote().orElseThrow();
        return new ResponseEntity<>(quote, HttpStatus.OK);
    }

}