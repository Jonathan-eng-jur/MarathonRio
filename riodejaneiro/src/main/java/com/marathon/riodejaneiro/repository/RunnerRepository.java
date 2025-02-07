package com.marathon.riodejaneiro.repository;

import com.marathon.riodejaneiro.model.Runner;
import com.marathon.riodejaneiro.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RunnerRepository extends JpaRepository<Runner, String> {
    Optional<Runner> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Workout> findWorkoutsByEmail(String email);
}
