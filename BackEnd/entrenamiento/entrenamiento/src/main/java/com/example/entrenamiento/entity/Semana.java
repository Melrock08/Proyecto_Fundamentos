package com.example.entrenamiento.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "semanas")
public class Semana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Número de semana dentro de la fase (1, 2, 3…)
    private Integer numeroSemana;

    // Lista de ejercicios: se almacenan como strings en una tabla de colección
    @ElementCollection
    @CollectionTable(
        name = "semana_ejercicios",
        joinColumns = @JoinColumn(name = "semana_id")
    )
    @Column(name = "ejercicio")
    private List<String> ejercicios = new ArrayList<>();

    // Relación Many-to-One con Fase
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fase_id")
    @JsonBackReference
    private Fase fase;

    // —— Constructores, getters y setters —— //

    public Semana() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumeroSemana() { return numeroSemana; }
    public void setNumeroSemana(Integer numeroSemana) { this.numeroSemana = numeroSemana; }

    public List<String> getEjercicios() { return ejercicios; }
    public void setEjercicios(List<String> ejercicios) { this.ejercicios = ejercicios; }

    public Fase getFase() { return fase; }
    public void setFase(Fase fase) { this.fase = fase; }
}

