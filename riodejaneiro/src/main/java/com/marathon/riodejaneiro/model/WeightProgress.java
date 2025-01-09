//package com.marathon.riodejaneiro.model;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDate;
//
//@Entity
//@Data
//public class WeightProgress {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private LocalDate date; // Data da medição
//
//    private Double weight; // Peso em quilogramas
//
//    private Double initialWeight; // Peso inicial
//
//    private Double targetWeight; // Peso objetivo/meta
//
//    private Double weeklyProgress; // Progresso semanal
//
//    @ManyToOne
//    @JoinColumn(name = "runner_id", nullable = false)
//    @JsonBackReference
//    private Runner runner;
//
//    public Double calculateWeeklyProgress(Double previousWeekWeight) {
//        if (previousWeekWeight == null || previousWeekWeight <= 0) {
//            return 0.0;
//        }
//        return previousWeekWeight - this.weight; // Progresso = peso anterior - peso atual
//    }
//
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Double getWeight() {
//        return weight;
//    }
//
//    public void setWeight(Double weight) {
//        this.weight = weight;
//    }
//
//    public Double getInitialWeight() {
//        return initialWeight;
//    }
//
//    public void setInitialWeight(Double initialWeight) {
//        this.initialWeight = initialWeight;
//    }
//
//    public Double getTargetWeight() {
//        return targetWeight;
//    }
//
//    public void setTargetWeight(Double targetWeight) {
//        this.targetWeight = targetWeight;
//    }
//
//    public Double getWeeklyProgress() {
//        return weeklyProgress;
//    }
//
//    public void setWeeklyProgress(Double weeklyProgress) {
//        this.weeklyProgress = weeklyProgress;
//    }
//
//    public Runner getRunner() {
//        return runner;
//    }
//
//    public void setRunner(Runner runner) {
//        this.runner = runner;
//    }
//}
