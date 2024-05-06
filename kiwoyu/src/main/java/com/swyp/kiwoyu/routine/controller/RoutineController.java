package com.swyp.kiwoyu.routine.controller;

import com.swyp.kiwoyu.routine.domain.*;
import com.swyp.kiwoyu.routine.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/routine")
public class RoutineController {

    @Autowired
    private RoutineService routineService;

//    @GetMapping
//    public ResponseEntity<List<Routine>> getAllRoutines() {
//        List<Routine> routines = routineService.getAllRoutines();
//        return new ResponseEntity<>(routines, HttpStatus.OK);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Routine> getRoutineById(@PathVariable("id") Long id) {
//        Optional<Routine> routine = routineService.getRoutineById(id);
//        return routine.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }


//    @GetMapping("/{userId}&{mandalartId}&{routineDate}")
//    public ResponseEntity<Collection<Routine>> getRoutineByIdAndDate(@PathVariable("userId") Long userId, @PathVariable("mandalartId") Long mandalartId,
//                                                                     @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date routineDate) {
//        Collection<Routine> routine = routineService.getRoutinesByUserMandalartAndDate(userId,mandalartId, routineDate);
//        return new ResponseEntity<>(routine, HttpStatus.OK);
//    }
    @PostMapping("/view")
    public ResponseEntity<PostRoutineByIdAndDateResponseDto> postRoutineByIdAndDate(
            @RequestBody PostRoutineByIdAndDateRequestDto dto
    ) {
        System.out.println("postRoutineByIdAndDate--start");
        List<RoutineDto> routine = routineService.getRoutinesByUserMandalartAndDate(dto.getUserId(),dto.getMandalartId(), dto.getRoutineDate());
        PostRoutineByIdAndDateResponseDto responseDto = new PostRoutineByIdAndDateResponseDto();
        responseDto.setRoutines(routine);
        System.out.println(routine);
        System.out.println("postRoutineByIdAndDate--end");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }



    @PostMapping("/create")
    public ResponseEntity<Routine> createRoutine(@RequestBody PostCreateRoutineRequestDto dto) {
        Routine createdRoutine = routineService.upsertRoutineWithUserAndMandalart(dto);
        return new ResponseEntity<>(createdRoutine, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Routine> updateRoutine(@PathVariable("id") Long id, @RequestBody Routine routine) {
        Optional<Routine> existingRoutine = routineService.getRoutineById(id);
        if (existingRoutine.isPresent()) {
            routine.setId(id);
            Routine updatedRoutine = routineService.createOrUpdateRoutine(routine);
            return new ResponseEntity<>(updatedRoutine, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable("id") Long id) {
        Optional<Routine> routine = routineService.getRoutineById(id);
        if (routine.isPresent()) {
            routineService.deleteRoutine(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}