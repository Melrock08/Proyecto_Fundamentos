package com.example.entrenamiento.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entrenamiento.dto.FaseDto;
import com.example.entrenamiento.dto.PlanDto;
import com.example.entrenamiento.dto.SemanaDto;
import com.example.entrenamiento.entity.Fase;
import com.example.entrenamiento.entity.Plan;
import com.example.entrenamiento.entity.Semana;
import com.example.entrenamiento.repository.PlanRepository;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanRepository planRepo;

    public PlanController(PlanRepository planRepo) {
        this.planRepo = planRepo;
    }

    @PostMapping
    public ResponseEntity<Plan> crearPlan(@RequestBody PlanDto planDto) {
        // 1) Crear la entidad Plan y asignar campos básicos
        Plan plan = new Plan();
        plan.setNombre(planDto.getNombre());
        plan.setDescripcion(planDto.getDescripcion());
        plan.setNivel(planDto.getNivel());
        plan.setDuracion(planDto.getDuracion());
        plan.setObjetivoGeneral(planDto.getObjetivoGeneral());
        plan.setPublico(planDto.getPublico());

        // 2) Recorrer cada FaseDto y convertirla en entidad Fase
        if (planDto.getFases() != null) {
            for (FaseDto faseDto : planDto.getFases()) {
                Fase fase = new Fase();
                fase.setNombre(faseDto.getNombre());
                fase.setPlan(plan);

                // 3) Recorrer cada SemanaDto dentro de la fase
                if (faseDto.getSemanas() != null) {
                    for (SemanaDto semanaDto : faseDto.getSemanas()) {
                        Semana semana = new Semana();
                        semana.setNumeroSemana(semanaDto.getNumeroSemana());
                        semana.setEjercicios(semanaDto.getEjercicios());
                        semana.setFase(fase);
                        fase.getSemanas().add(semana);
                    }
                }
                plan.getFases().add(fase);
            }
        }

        // 4) Guardar en la base de datos (cascade persiste fases y semanas)
        Plan guardado = planRepo.save(plan);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // ============================================
    //  NUEVOS MÉTODOS GET PARA LISTAR Y CONSULTAR
    // ============================================

    /** 
     * GET /api/plans 
     * Devuelve la lista completa de planes (con sus fases y semanas).
     */
    @GetMapping
    public ResponseEntity<List<Plan>> listarPlanes() {
        List<Plan> planes = planRepo.findAll();
        return ResponseEntity.ok(planes);
    }

    /** 
     * GET /api/plans/{id} 
     * Devuelve un plan en particular por su ID (con sus fases y semanas).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Plan> obtenerPlanPorId(@PathVariable Long id) {
        Optional<Plan> optPlan = planRepo.findById(id);
        if (optPlan.isPresent()) {
            return ResponseEntity.ok(optPlan.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
