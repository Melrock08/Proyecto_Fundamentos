package com.example.entrenamiento.repository;

import com.example.entrenamiento.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    // Aquí puedes añadir métodos de búsqueda personalizados si los necesitas
}
