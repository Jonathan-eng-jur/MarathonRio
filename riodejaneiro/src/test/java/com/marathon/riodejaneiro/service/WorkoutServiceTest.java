package com.marathon.riodejaneiro.service;

import com.marathon.riodejaneiro.model.Runner;
import com.marathon.riodejaneiro.model.TrainingWeek;
import com.marathon.riodejaneiro.model.Workout;
import com.marathon.riodejaneiro.repository.RunnerRepository;
import com.marathon.riodejaneiro.repository.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private RunnerRepository runnerRepository;

    @InjectMocks
    private WorkoutService workoutService;

    private Runner createRunner() {
        String email = "runner@email.com";
        Runner runner = new Runner();
        runner.setEmail(email);
        return runner;
    }

    private TrainingWeek createTrainingWeek() {
        TrainingWeek trainingWeek = new TrainingWeek();
        trainingWeek.setId(1L);
        return trainingWeek;
    }

    private Workout createWorkout() {
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
    void testSaveWorkout() {
        Runner runner = createRunner();
        Workout workout = createWorkout();
        when(runnerRepository.findById(runner.getEmail())).thenReturn(Optional.of(runner));
        when(workoutRepository.save(Mockito.<Workout>any())).thenReturn(workout);
        Workout savedWorkout = workoutService.save(runner.getEmail(), workout);

        assertNotNull(savedWorkout);
        assertEquals(1, savedWorkout.getWeek());
        verify(runnerRepository, times(1)).findById(runner.getEmail());
        verify(workoutRepository, times(1)).save(workout);
    }

    @Test
    void testSaveWorkout_RunnerNotFound() {
        String email = "nonexistent@email.com";
        Workout workout = createWorkout();
        when(runnerRepository.findById(email)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            workoutService.save(email, workout);
        });

        assertEquals("Runner not found with email: " + email, exception.getMessage());
        verify(runnerRepository, times(1)).findById(email);
        verify(workoutRepository, never()).save(any());
    }

    @Test
    void testSaveWorkout_WithTrainingWeek() {
        Runner runner = createRunner();
        Workout workout = createWorkout();
        when(runnerRepository.findById(runner.getEmail())).thenReturn(Optional.of(runner));
        when(workoutRepository.save(Mockito.<Workout>any())).thenReturn(workout);
        Workout savedWorkout = workoutService.save(runner.getEmail(), workout);

        assertNotNull(savedWorkout);
        assertEquals(1, savedWorkout.getWeek());
        assertNotNull(savedWorkout.getTrainingWeek());
        verify(runnerRepository, times(1)).findById(runner.getEmail());
        verify(workoutRepository, times(1)).save(workout);
    }

    @Test
    void testSaveWorkout_CalculatePace() {
        Runner runner = createRunner();
        Workout workout = createWorkout();
        workout.setLongRunDistance(10.0);
        workout.setLongRunDurationMinutes(LocalTime.of(1, 0, 0));
        when(runnerRepository.findById(runner.getEmail())).thenReturn(Optional.of(runner));
        when(workoutRepository.save(Mockito.<Workout>any())).thenReturn(workout);
        Workout savedWorkout = workoutService.save(runner.getEmail(), workout);

        assertNotNull(savedWorkout);
        assertEquals(LocalTime.of(0, 6, 0), savedWorkout.getLongRunPace());
        verify(runnerRepository, times(1)).findById(runner.getEmail());
        verify(workoutRepository, times(1)).save(workout);
    }

    @Test
    void testCalculatePace() {
        LocalTime pace = workoutService.calculatePace(10.0, LocalTime.of(1, 0, 0));
        assertEquals(LocalTime.of(0, 6, 0), pace);
    }

    @Test
    void testCalculatePace_InvalidDistance() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            workoutService.calculatePace(0, LocalTime.of(1, 0, 0));
        });

        assertEquals("A distância deve ser maior que zero", exception.getMessage());
    }

    @Test
    void testGetAllWorkoutsForRunner() {
        Runner runner = createRunner();
        Workout workout1 = createWorkout();
        Workout workout2 = createWorkout();
        when(workoutRepository.findByRunnerEmail(runner.getEmail())).thenReturn(List.of(workout1, workout2));
        List<Workout> workouts = workoutService.getAllWorkoutsForRunner(runner.getEmail());

        assertEquals(2, workouts.size());
        verify(workoutRepository, times(1)).findByRunnerEmail(runner.getEmail());
    }

    @Test
    void testGetAllWorkouts() {
        Workout workout1 = createWorkout();
        Workout workout2 = createWorkout();
        when(workoutRepository.findAll()).thenReturn(List.of(workout1, workout2));
        List<Workout> workouts = workoutService.getAllWorkouts();

        assertEquals(2, workouts.size());
        verify(workoutRepository, times(1)).findAll();
    }

    @Test
    void testUpdateWorkout() {
        Runner runner = createRunner();
        Workout workout = createWorkout();
        TrainingWeek trainingWeek = createTrainingWeek();
        double distance = 15.0;
        LocalTime duration = LocalTime.of(1, 30, 0);
        when(workoutRepository.findByRunnerEmailAndWeek(runner.getEmail(), workout.getWeek())).thenReturn(workout);
        when(workoutRepository.save(Mockito.<Workout>any())).thenReturn(workout);
        Workout updatedWorkout = workoutService.updateWorkout(runner.getEmail(), workout.getWeek(), distance, duration, trainingWeek);

        assertNotNull(updatedWorkout);
        assertEquals(distance, updatedWorkout.getLongRunDistance());
        assertEquals(duration, updatedWorkout.getLongRunDurationMinutes());
        assertEquals(trainingWeek, updatedWorkout.getTrainingWeek());
        verify(workoutRepository, times(1)).findByRunnerEmailAndWeek(runner.getEmail(), workout.getWeek());
        verify(workoutRepository, times(1)).save(workout);
    }

    @Test
    void testUpdateWorkout_CalculatePace() {
        Runner runner = createRunner();
        Workout workout = createWorkout();
        TrainingWeek trainingWeek = createTrainingWeek();
        double distance = 10.0;
        LocalTime duration = LocalTime.of(1, 0, 0);
        when(workoutRepository.findByRunnerEmailAndWeek(runner.getEmail(), workout.getWeek())).thenReturn(workout);
        when(workoutRepository.save(Mockito.<Workout>any())).thenReturn(workout);
        Workout updatedWorkout = workoutService.updateWorkout(runner.getEmail(), workout.getWeek(), distance, duration, trainingWeek);

        assertNotNull(updatedWorkout);
        assertEquals(LocalTime.of(0, 6, 0), updatedWorkout.getLongRunPace());
        verify(workoutRepository, times(1)).findByRunnerEmailAndWeek(runner.getEmail(), workout.getWeek());
        verify(workoutRepository, times(1)).save(workout);
    }
}