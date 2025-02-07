package com.marathon.riodejaneiro.controller;

import com.marathon.riodejaneiro.model.Runner;
import com.marathon.riodejaneiro.model.Workout;
import com.marathon.riodejaneiro.service.RunnerService;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class RunnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private RunnerController runnerController;

    @Mock
    private RunnerService runnerService;

    public Runner createRunner() {
        String email = "runner@email.com";
        Runner runner = new Runner();
        runner.setEmail(email);
        return runner;
    }

    @Test
    void testGetRunnerByEmail() {
        Runner runner = createRunner();
        when(runnerService.getRunner(runner.getEmail())).thenReturn(Optional.of(runner));
        Optional<Runner> result = runnerController.getRunner(runner.getEmail());

        assertEquals(runner, result.get());
        verify(runnerService, times(1)).getRunner(runner.getEmail());
    }

    @Test
    void testGetWorkoutsForRunner() {
        Runner runner = createRunner();
        List<Workout> workouts = Arrays.asList(new Workout(), new Workout());
        when(runnerService.getWorkoutsForRunner(runner.getEmail())).thenReturn(workouts);
        ResponseEntity<List<Workout>> response = runnerController.getWorkoutsForRunner(runner.getEmail());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(workouts, response.getBody());
        verify(runnerService, times(1)).getWorkoutsForRunner(runner.getEmail());
    }

    @Test
    void testGetWorkoutsForRunnerNotFound() {
        String email = "runner@email.com";
        when(runnerService.getWorkoutsForRunner(email)).thenReturn(Collections.emptyList());
        ResponseEntity<List<Workout>> response = runnerController.getWorkoutsForRunner(email);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(runnerService, times(1)).getWorkoutsForRunner(email);
    }

    @Test
    void testGetAllRunners() {
        List<Runner> runners = Arrays.asList(new Runner(), new Runner());
        when(runnerService.getAllRunner()).thenReturn(runners);
        List<Runner> result = runnerController.getRunnerAll();

        assertEquals(runners, result);
        verify(runnerService, times(1)).getAllRunner();
    }

    @Test
    void testCreateOrUpdateRunner() {
        Runner runner = createRunner();
        when(runnerService.existsById(runner.getEmail())).thenReturn(false);
        when(runnerService.createOrUpdateRunner(runner)).thenReturn(runner);
        ResponseEntity<Runner> response = runnerController.createOrUpdateRunner(runner);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(runner, response.getBody());
        verify(runnerService, times(1)).existsById(runner.getEmail());
        verify(runnerService, times(1)).createOrUpdateRunner(runner);
    }

    @Test
    void testUpdateRunner() {
        Runner runner = createRunner();
        when(runnerService.existsById(runner.getEmail())).thenReturn(true);
        when(runnerService.createOrUpdateRunner(runner)).thenReturn(runner);
        ResponseEntity<Runner> response = runnerController.createOrUpdateRunner(runner);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(runner, response.getBody());
        verify(runnerService, times(1)).existsById(runner.getEmail());
        verify(runnerService, times(1)).createOrUpdateRunner(runner);
    }

}

