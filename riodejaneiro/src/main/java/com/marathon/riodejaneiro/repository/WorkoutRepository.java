package com.marathon.riodejaneiro.repository;

import com.marathon.riodejaneiro.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByRunnerEmail(String email);

    Workout findByRunnerEmailAndWeek(String email, int week);
}
