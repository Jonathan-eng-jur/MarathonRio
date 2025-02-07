package com.marathon.riodejaneiro.service;

import com.marathon.riodejaneiro.model.Runner;
import com.marathon.riodejaneiro.model.Workout;
import com.marathon.riodejaneiro.repository.RunnerRepository;
import com.marathon.riodejaneiro.repository.WorkoutRepository;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RunnerService {

    @Autowired
    private RunnerRepository runnerRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    public Optional<Runner> getRunner(String email) {
        return runnerRepository.findByEmail(email);
    }

    public List<Workout> getWorkoutsForRunner(String email) {
        Optional<Runner> runnerOptional = runnerRepository.findByEmail(email);
        if (runnerOptional.isPresent()) {
            return runnerOptional.get().getRunningWorkouts();
        }
        return List.of();
    }

    public Runner createOrUpdateRunner(Runner runner) {
        Optional<Runner> existingRunner = runnerRepository.findByEmail(runner.getEmail());
        if (existingRunner.isPresent()) {
            Runner existing = existingRunner.get();
            existing.setName(runner.getName());
            existing.setRunningWorkouts(runner.getRunningWorkouts());
            return runnerRepository.save(existing);
        }

        return runnerRepository.save(runner);
    }

    public List<Runner> getAllRunner() {
        return runnerRepository.findAll();
    }

    public boolean existsById(String email) {
        return runnerRepository.existsByEmail(email);
    }
}
