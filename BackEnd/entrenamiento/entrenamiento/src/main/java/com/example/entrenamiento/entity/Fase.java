package com.example.entrenamiento.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "fases")
public class Fase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de la fase, p. ej. “Fase de fuerza”
    private String nombre;

    // Relación Many-to-One con Plan
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    @JsonBackReference
    private Plan plan;


    @OneToMany(mappedBy = "fase", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Semana> semanas = new ArrayList<>();

    // —— Constructores, getters y setters —— //

    public Fase() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Plan getPlan() { return plan; }
    public void setPlan(Plan plan) { this.plan = plan; }

    public List<Semana> getSemanas() { return semanas; }
    public void setSemanas(List<Semana> semanas) { this.semanas = semanas; }
}
