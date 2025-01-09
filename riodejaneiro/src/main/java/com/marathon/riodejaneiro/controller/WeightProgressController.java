//package com.marathon.riodejaneiro.controller;
//
//import com.marathon.riodejaneiro.model.WeightProgress;
//import com.marathon.riodejaneiro.service.WeightProgressService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/weight-progress")
//public class WeightProgressController {
//
//    @Autowired
//    private WeightProgressService weightProgressService;
//
//    // Endpoint para criar um novo WeightProgress
//    @PostMapping("/{runnerId}")
//    public ResponseEntity<WeightProgress> createWeightProgress(@PathVariable String runnerId, @RequestBody WeightProgress weightProgress) {
//        WeightProgress createdWeightProgress = weightProgressService.createWeightProgress(runnerId, weightProgress);
//        return new ResponseEntity<>(createdWeightProgress, HttpStatus.CREATED);
//    }
//
//    // Endpoint para obter todos os WeightProgress de um Runner
//    @GetMapping("/runner/{runnerId}")
//    public ResponseEntity<List<WeightProgress>> getWeightProgressByRunner(@PathVariable String runnerId) {
//        List<WeightProgress> weightProgressList = weightProgressService.getWeightProgressByRunner(runnerId);
//        return new ResponseEntity<>(weightProgressList, HttpStatus.OK);
//    }
//
//    // Endpoint para obter um WeightProgress específico
//    @GetMapping("/{id}")
//    public ResponseEntity<WeightProgress> getWeightProgressById(@PathVariable Long id) {
//        WeightProgress weightProgress = weightProgressService.getWeightProgressById(id);
//        return new ResponseEntity<>(weightProgress, HttpStatus.OK);
//    }
//}