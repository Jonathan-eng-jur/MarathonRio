package com.marathon.riodejaneiro.controller;

import com.marathon.riodejaneiro.model.Runner;
import com.marathon.riodejaneiro.model.TrainingWeek;
import com.marathon.riodejaneiro.model.Workout;
import com.marathon.riodejaneiro.service.WorkoutService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class WorkoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private WorkoutController workoutController;

    @Mock
    private WorkoutService workoutService;

    public Runner createRunner() {
        String email = "runner@email.com";
        Runner runner = new Runner();
        runner.setEmail(email);
        return runner;
    }

    public TrainingWeek createTrainingWeek() {
        TrainingWeek trainingWeek = new TrainingWeek();
        trainingWeek.setId(1L);
        return trainingWeek;
    }

    public Workout createWorkout() {
        Runner runner = createRunner();
        TrainingWeek trainingWeek = createTrainingWeek();
        Workout workout = new Workout();
        workout.setId(1L);
        workout.setWeek(1);
        workout.setTrainingWeek(trainingWeek);
        workout.setLongRunDistance(10);
        workout.setLongRunDurationMinutes(LocalTime.of(0, 59, 0));
        workout.setRunner(runner);
        return workout;
    }

    @Test
    void testGetWorkout() {
        Runner runner = createRunner();
        Workout workout = createWorkout();
        Workout workout2 = createWorkout();
        List<Workout> mockWorkouts = List.of(workout, workout2);
        when(workoutService.getAllWorkoutsForRunner(runner.getEmail())).thenReturn(mockWorkouts);
        List<Workout> result = workoutController.getWorkoutsForRunner(runner.getEmail());

        assertEquals(runner, result.get(1).getRunner());
        verify(workoutService, times(1)).getAllWorkoutsForRunner(runner.getEmail());
    }

    @Test
    void testCreateWorkout() {
        Runner runner = createRunner();
        Workout workout = createWorkout();
        when(workoutService.save(runner.getEmail(), workout)).thenReturn(workout);
        ResponseEntity<Workout> response = workoutController.createWorkout(runner.getEmail(), workout);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(workout, response.getBody());
        verify(workoutService, times(1)).save(runner.getEmail(), workout);
    }

    @Test
    void testGetAllWorkouts() {
        Workout workout1 = createWorkout();
        Workout workout2 = createWorkout();
        List<Workout> mockWorkouts = List.of(workout1, workout2);
        when(workoutService.getAllWorkouts()).thenReturn(mockWorkouts);
        List<Workout> result = workoutController.getWorkoutsAll();

        assertEquals(2, result.size());
        verify(workoutService, times(1)).getAllWorkouts();
    }

    @Test
    void testUpdateWorkout() {
        Runner runner = createRunner();
        Workout workout = createWorkout();
        TrainingWeek trainingWeek = createTrainingWeek();
        double distance = 12.0;
        LocalTime duration = LocalTime.of(1, 10, 0);
        when(workoutService.updateWorkout(runner.getEmail(), workout.getWeek(), distance, duration, trainingWeek)).thenReturn(workout);
        ResponseEntity<Workout> response = workoutController.updateLongPace(
                runner.getEmail(), 1, distance, duration, trainingWeek);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(workout, response.getBody());
        verify(workoutService, times(1)).updateWorkout(runner.getEmail(), 1, distance, duration, trainingWeek);
    }

    @Test
    void testUpdateWorkout_NotFound() {
        Runner runner = createRunner();
        int workoutId = 99;
        double distance = 12.0;
        LocalTime duration = LocalTime.of(1, 10, 0);
        TrainingWeek trainingWeek = createTrainingWeek();
        when(workoutService.updateWorkout(runner.getEmail(), workoutId, distance, duration, trainingWeek))
                .thenThrow(new EntityNotFoundException());
        ResponseEntity<Workout> response = workoutController.updateLongPace(
                runner.getEmail(), workoutId, distance, duration, trainingWeek);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(workoutService, times(1)).updateWorkout(runner.getEmail(), workoutId, distance, duration, trainingWeek);
    }
}
