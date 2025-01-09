package com.marathon.riodejaneiro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int week;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "training_week_id")
    @JsonManagedReference
    private TrainingWeek trainingWeek;

    private double longRunDistance;

    private LocalTime longRunDurationMinutes;

    private LocalTime longRunPace;

    @ManyToOne
    @JoinColumn(name = "runner_id")
    @JsonBackReference
    private Runner runner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public TrainingWeek getTrainingWeek() {
        return trainingWeek;
    }

    public void setTrainingWeek(TrainingWeek trainingWeek) {
        this.trainingWeek = trainingWeek;
    }

    public double getLongRunDistance() {
        return longRunDistance;
    }

    public void setLongRunDistance(double longRunDistance) {
        this.longRunDistance = longRunDistance;
    }

    public LocalTime getLongRunDurationMinutes() {
        return longRunDurationMinutes;
    }

    public void setLongRunDurationMinutes(LocalTime longRunDurationMinutes) {
        this.longRunDurationMinutes = longRunDurationMinutes;
    }

    public LocalTime getLongRunPace() {
        return longRunPace;
    }

    public void setLongRunPace(LocalTime longRunPace) {
        this.longRunPace = longRunPace;
    }

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }
}
