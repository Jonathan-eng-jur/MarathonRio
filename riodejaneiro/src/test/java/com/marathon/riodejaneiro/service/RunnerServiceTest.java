package com.marathon.riodejaneiro.service;

import com.marathon.riodejaneiro.model.Runner;
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
class RunnerServiceTest {

    @Mock
    private RunnerRepository runnerRepository;

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private RunnerService runnerService;

    private Runner createRunner() {
        String email = "runner@email.com";
        Runner runner = new Runner();
        runner.setEmail(email);
        runner.setName("Runner Name");
        return runner;
    }

    private Workout createWorkout() {
        Workout workout = new Workout();
        workout.setId(1L);
        workout.setWeek(1);
        workout.setLongRunDistance(10);
        workout.setLongRunDurationMinutes(LocalTime.of(0, 59, 0));
        return workout;
    }

    @Test
    void testGetRunner() {
        Runner runner = createRunner();
        when(runnerRepository.findByEmail(runner.getEmail())).thenReturn(Optional.of(runner));

        Optional<Runner> result = runnerService.getRunner(runner.getEmail());

        assertEquals(runner.getEmail(), result.get().getEmail());
        verify(runnerRepository, times(1)).findByEmail(runner.getEmail());
    }

    @Test
    void testGetRunnerNotFound() {
        String email = "notfound@email.com";
        when(runnerRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<Runner> result = runnerService.getRunner(email);

        assertTrue(result.isEmpty());
        verify(runnerRepository, times(1)).findByEmail(email);
    }

    @Test
    void testGetWorkoutsForRunner() {
        Runner runner = createRunner();
        List<Workout> workouts = List.of(createWorkout(), createWorkout());
        runner.setRunningWorkouts(workouts);

        when(runnerRepository.findByEmail(runner.getEmail())).thenReturn(Optional.of(runner));

        List<Workout> result = runnerService.getWorkoutsForRunner(runner.getEmail());

        assertEquals(2, result.size());
        verify(runnerRepository, times(1)).findByEmail(runner.getEmail());
    }

    @Test
    void testGetWorkoutsForRunner_NotFound() {
        String email = "notfound@email.com";
        when(runnerRepository.findByEmail(email)).thenReturn(Optional.empty());

        List<Workout> result = runnerService.getWorkoutsForRunner(email);

        assertTrue(result.isEmpty());
        verify(runnerRepository, times(1)).findByEmail(email);
    }

    @Test
    void testCreateOrUpdateRunner_NewRunner() {
        Runner newRunner = createRunner();
        newRunner.setEmail("new@runner.com");

        when(runnerRepository.findByEmail(newRunner.getEmail())).thenReturn(Optional.empty());
        when(runnerRepository.save(newRunner)).thenReturn(newRunner);

        Runner result = runnerService.createOrUpdateRunner(newRunner);

        assertEquals(newRunner.getName(), result.getName());
        verify(runnerRepository, times(1)).findByEmail(newRunner.getEmail());
        verify(runnerRepository, times(1)).save(newRunner);
    }

    @Test
    void testCreateOrUpdateRunner_UpdateExistingRunner() {
        Runner existingRunner = createRunner();
        Runner updatedRunner = createRunner();
        updatedRunner.setName("Updated Name");

        when(runnerRepository.findByEmail(existingRunner.getEmail())).thenReturn(Optional.of(existingRunner));
        when(runnerRepository.save(Mockito.<Runner>any())).thenAnswer(invocation -> invocation.getArgument(0));

        Runner result = runnerService.createOrUpdateRunner(updatedRunner);

        assertEquals("Updated Name", result.getName());
        verify(runnerRepository, times(1)).findByEmail(existingRunner.getEmail());
        verify(runnerRepository, times(1)).save(existingRunner);
    }

    @Test
    void testGetAllRunner() {
        List<Runner> runners = List.of(createRunner(), createRunner(), createRunner());

        when(runnerRepository.findAll()).thenReturn(runners);

        List<Runner> result = runnerService.getAllRunner();

        assertEquals(3, result.size());
        verify(runnerRepository, times(1)).findAll();
    }

    @Test
    void testExistsById() {
        String email = "runner@email.com";
        when(runnerRepository.existsByEmail(email)).thenReturn(true);

        boolean result = runnerService.existsById(email);

        assertTrue(result);
        verify(runnerRepository, times(1)).existsByEmail(email);
    }

    @Test
    void testExistsById_NotFound() {
        String email = "notfound@email.com";
        when(runnerRepository.existsByEmail(email)).thenReturn(false);

        boolean result = runnerService.existsById(email);

        assertFalse(result);
        verify(runnerRepository, times(1)).existsByEmail(email);
    }
}
