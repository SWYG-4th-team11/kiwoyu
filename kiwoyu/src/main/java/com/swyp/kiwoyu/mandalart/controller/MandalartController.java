package com.swyp.kiwoyu.mandalart.controller;

import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.mandalart.service.MandalartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mandalart")
public class MandalartController {

    @Autowired
    private MandalartService mandalartService;

    @GetMapping
    public ResponseEntity<List<Mandalart>> getAllMandalarts() {
        List<Mandalart> mandalarts = mandalartService.getAllMandalarts();
        return new ResponseEntity<>(mandalarts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mandalart> getMandalartById(@PathVariable("id") Long id) {
        Optional<Mandalart> mandalart = mandalartService.getMandalartById(id);
        return mandalart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Mandalart> createMandalart(@RequestBody Mandalart mandalart, Long userId) {
        Mandalart createdMandalart = mandalartService.createOrUpdateMandalartWithUserId(mandalart, userId);
        return new ResponseEntity<>(createdMandalart, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mandalart> updateMandalart(@PathVariable("id") Long id, @RequestBody Mandalart mandalart) {
        Optional<Mandalart> existingMandalart = mandalartService.getMandalartById(id);
        if (existingMandalart.isPresent()) {
            mandalart.setId(id);
            Mandalart updatedMandalart = mandalartService.createOrUpdateMandalart(mandalart);
            return new ResponseEntity<>(updatedMandalart, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMandalart(@PathVariable("id") Long id) {
        Optional<Mandalart> mandalart = mandalartService.getMandalartById(id);
        if (mandalart.isPresent()) {
            mandalartService.deleteMandalart(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}