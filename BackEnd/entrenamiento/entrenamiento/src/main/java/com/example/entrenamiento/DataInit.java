package com.example.entrenamiento;

import com.example.entrenamiento.entity.Plan;
import com.example.entrenamiento.repository.PlanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInit {

    @Bean
    CommandLineRunner initData(PlanRepository planRepository) {
        return args -> {
            // Verifica si ya hay datos en la base de datos antes de agregar los planes iniciales
            if (planRepository.count() == 0) {
                // Crear varios planes de ejemplo solo si la base de datos está vacía

                Plan plan1 = new Plan();
                plan1.setNombre("Juan Pérez");
                plan1.setEdad(28);
                plan1.setSexo("Masculino");
                plan1.setEstatura(175);
                plan1.setPeso(70);
                plan1.setDuracion(12);
                plan1.setFrecuencia(4);
                plan1.setIntensidad("Media");
                plan1.setObjetivo("Ganar músculo");
                plan1.setActividad("Moderado");
                plan1.setFechaInicio(java.time.LocalDate.now());
                plan1.setNotas("Quiere mejorar fuerza y resistencia.");

                planRepository.save(plan1);

                Plan plan2 = new Plan();
                plan2.setNombre("Ana Gómez");
                plan2.setEdad(24);
                plan2.setSexo("Femenino");
                plan2.setEstatura(160);
                plan2.setPeso(55);
                plan2.setDuracion(8);
                plan2.setFrecuencia(3);
                plan2.setIntensidad("Baja");
                plan2.setObjetivo("Perder peso");
                plan2.setActividad("Ligero");
                plan2.setFechaInicio(java.time.LocalDate.now().minusDays(7));
                plan2.setNotas("Busca tonificar y mejorar la salud cardiovascular.");

                planRepository.save(plan2);

                Plan plan3 = new Plan();
                plan3.setNombre("Carlos Martínez");
                plan3.setEdad(35);
                plan3.setSexo("Masculino");
                plan3.setEstatura(180);
                plan3.setPeso(85);
                plan3.setDuracion(16);
                plan3.setFrecuencia(5);
                plan3.setIntensidad("Alta");
                plan3.setObjetivo("Ganar fuerza");
                plan3.setActividad("Intenso");
                plan3.setFechaInicio(java.time.LocalDate.now().minusDays(10));
                plan3.setNotas("Busca aumentar la fuerza máxima y la masa muscular.");

                planRepository.save(plan3);

                Plan plan4 = new Plan();
                plan4.setNombre("Laura Sánchez");
                plan4.setEdad(30);
                plan4.setSexo("Femenino");
                plan4.setEstatura(165);
                plan4.setPeso(63);
                plan4.setDuracion(10);
                plan4.setFrecuencia(4);
                plan4.setIntensidad("Media");
                plan4.setObjetivo("Mejorar la resistencia");
                plan4.setActividad("Moderado");
                plan4.setFechaInicio(java.time.LocalDate.now().minusDays(5));
                plan4.setNotas("Busca mejorar su resistencia aeróbica y general.");

                planRepository.save(plan4);

                Plan plan5 = new Plan();
                plan5.setNombre("Luis Ramírez");
                plan5.setEdad(40);
                plan5.setSexo("Masculino");
                plan5.setEstatura(178);
                plan5.setPeso(90);
                plan5.setDuracion(20);
                plan5.setFrecuencia(3);
                plan5.setIntensidad("Baja");
                plan5.setObjetivo("Recuperación post-cirugía");
                plan5.setActividad("Baja");
                plan5.setFechaInicio(java.time.LocalDate.now().minusMonths(1));
                plan5.setNotas("Recuperación y trabajo de movilidad tras una operación.");

                planRepository.save(plan5);

                System.out.println("✅ Planes de ejemplo guardados correctamente.");
            }
        };
    }
}
