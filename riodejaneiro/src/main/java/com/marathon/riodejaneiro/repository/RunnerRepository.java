package com.marathon.riodejaneiro.repository;

import com.marathon.riodejaneiro.model.Runner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunnerRepository extends JpaRepository<Runner, String> {
}
