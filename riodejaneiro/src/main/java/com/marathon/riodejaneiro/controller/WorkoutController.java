package com.marathon.riodejaneiro.controller;

import com.marathon.riodejaneiro.model.TrainingWeek;
import com.marathon.riodejaneiro.model.Workout;
import com.marathon.riodejaneiro.service.WorkoutService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/workout")
@CrossOrigin(origins = "*")
public class WorkoutController {

    @Autowired
    private WorkoutService runningWorkoutService;

    @PostMapping("/{email}")
    public ResponseEntity<Workout> createWorkout(@PathVariable String email, @Valid @RequestBody Workout workout) {
        Workout createdWorkout = runningWorkoutService.save(email, workout);
        return new ResponseEntity<>(createdWorkout, HttpStatus.CREATED);
    }

    @GetMapping("/runner/{email}")
    public List<Workout> getWorkoutsForRunner(@PathVariable String email) {
        return runningWorkoutService.getAllWorkoutsForRunner(email);
    }

    @GetMapping("/runner/all")
    public List<Workout> getWorkoutsAll() {
        return runningWorkoutService.getAllWorkouts();
    }

    @PutMapping("/{email}/{workoutId}")
    public ResponseEntity<Workout> updateLongPace(
            @PathVariable String email,
            @PathVariable int workoutId,
            @RequestParam double distance,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime duration,
            @Valid @RequestBody(required = false) TrainingWeek trainingWeek) {
        try {
            Workout updatedWorkout = runningWorkoutService.updateWorkout(email, workoutId, distance, duration, trainingWeek);
            return new ResponseEntity<>(updatedWorkout, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
