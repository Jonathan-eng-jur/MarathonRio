//package com.marathon.riodejaneiro.service;
//
//import com.marathon.riodejaneiro.model.Runner;
//import com.marathon.riodejaneiro.model.WeightProgress;
//import com.marathon.riodejaneiro.repository.RunnerRepository;
//import com.marathon.riodejaneiro.repository.WeightProgressRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class WeightProgressService {
//
//    @Autowired
//    private WeightProgressRepository weightProgressRepository;
//
//    @Autowired
//    private RunnerRepository runnerRepository;
//
//    // Método para criar um novo WeightProgress
//    public WeightProgress createWeightProgress(String runnerId, WeightProgress weightProgress) {
//        // Verificar se o Runner existe
//        Runner runner = runnerRepository.findById(runnerId)
//                .orElseThrow(() -> new RuntimeException("Runner not found"));
//
//        weightProgress.setRunner(runner);
//        return weightProgressRepository.save(weightProgress);
//    }
//
//    // Método para obter todos os WeightProgress de um Runner
//    public List<WeightProgress> getWeightProgressByRunner(String runnerId) {
//        return weightProgressRepository.findByRunnerEmail(runnerId);
//    }
//
//    // Método para obter um WeightProgress específico
//    public WeightProgress getWeightProgressById(Long id) {
//        return weightProgressRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("WeightProgress not found"));
//    }
//}
