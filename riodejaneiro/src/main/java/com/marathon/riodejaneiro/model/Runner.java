package com.marathon.riodejaneiro.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Runner {

    @Id
    @Email(message = "Por favor, insira um e-mail válido")
    private String email; // O e-mail será a chave única

    @NotNull(message = "O nome não pode ser nulo")
    private String name;

    @OneToMany(mappedBy = "runner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Workout> runningWorkouts = new ArrayList<>();

    public @NotNull(message = "O nome não pode ser nulo") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "O nome não pode ser nulo") String name) {
        this.name = name;
    }

    public @Email(message = "Por favor, insira um e-mail válido") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Por favor, insira um e-mail válido") String email) {
        this.email = email;
    }

    public List<Workout> getRunningWorkouts() {
        return runningWorkouts;
    }

    public void setRunningWorkouts(List<Workout> runningWorkouts) {
        this.runningWorkouts = runningWorkouts;
    }
}
