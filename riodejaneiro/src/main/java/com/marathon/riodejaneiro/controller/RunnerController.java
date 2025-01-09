package com.marathon.riodejaneiro.controller;

import com.marathon.riodejaneiro.model.Runner;
import com.marathon.riodejaneiro.model.Workout;
import com.marathon.riodejaneiro.service.RunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/runner")
public class RunnerController {

    @Autowired
    private RunnerService runnerService;

    @GetMapping("/get/{email}")
    public Optional<Runner> getRunner(@PathVariable String email) {
        return runnerService.getRunner(email);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Workout>> getWorkoutsForRunner(@PathVariable String email) {
        List<Workout> workouts = runnerService.getWorkoutsForRunner(email);
        if (workouts.isEmpty()) {
            return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar
        }
        return ResponseEntity.ok(workouts); // Retorna 200 com a lista de treinos
    }

    @GetMapping("/all")
    public List<Runner> getRunnerAll() {
        return runnerService.getAllRunner();
    }

    @PostMapping
    public ResponseEntity<Runner> createOrUpdateRunner(@RequestBody Runner runner) {
        boolean isNew = !runnerService.existsById(runner.getEmail()); // Verifica se é um novo corredor
        Runner savedRunner = runnerService.createOrUpdateRunner(runner);

        if (isNew) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRunner);
        }
        return ResponseEntity.ok(savedRunner);
    }
}

