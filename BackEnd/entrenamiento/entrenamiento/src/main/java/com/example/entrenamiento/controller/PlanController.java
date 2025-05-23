package com.example.entrenamiento.controller;

import com.example.entrenamiento.entity.Plan;
import com.example.entrenamiento.repository.PlanRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanRepository repository;

    public PlanController(PlanRepository repository) {
        this.repository = repository;
    }

    // 1) Obtener todos los planes
    @GetMapping
    public List<Plan> getAllPlans() {
        return repository.findAll();
    }

    // 2) Obtener un plan por ID
    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3) Crear un nuevo plan
    @PostMapping
    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) {
        Plan saved = repository.save(plan);
        return ResponseEntity.ok(saved);
    }
}
