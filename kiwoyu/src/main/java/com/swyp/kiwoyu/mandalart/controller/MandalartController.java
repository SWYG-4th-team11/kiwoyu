package com.swyp.kiwoyu.mandalart.controller;

import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.mandalart.dto.GetMandalartDto;
import com.swyp.kiwoyu.mandalart.dto.PostMandalartDto;
import com.swyp.kiwoyu.mandalart.dto.UpdateMandalartRequestDto;
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

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<GetMandalartDto>> getMandalartWholeInfoByUserId(@PathVariable("userId") Long userId) {
        List<GetMandalartDto> mandalarts = mandalartService.getMandalartWholeInfo(userId);
        mandalartService.initializeLevelup(mandalarts);
        return new ResponseEntity<>(mandalarts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Mandalart> createMandalart(@RequestBody PostMandalartDto dto) {
        Mandalart createdMandalart = mandalartService.createOrUpdateMandalartWithUserId(dto);
        return new ResponseEntity<>(createdMandalart, HttpStatus.CREATED);
    }

    @PutMapping("/category")
    public ResponseEntity<UpdateMandalartRequestDto> updateMandalartCategory(@RequestBody UpdateMandalartRequestDto dto ) {
        UpdateMandalartRequestDto res = mandalartService.updateMandalartCategory(dto);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
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