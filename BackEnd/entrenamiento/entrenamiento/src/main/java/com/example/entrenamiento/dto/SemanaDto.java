package com.example.entrenamiento.dto;

import java.util.List;

public class SemanaDto {
    private Integer numeroSemana;
    private List<String> ejercicios;

    // GETTERS
    public Integer getNumeroSemana() {
        return numeroSemana;
    }

    public List<String> getEjercicios() {
        return ejercicios;
    }

    // SETTERS
    public void setNumeroSemana(Integer numeroSemana) {
        this.numeroSemana = numeroSemana;
    }

    public void setEjercicios(List<String> ejercicios) {
        this.ejercicios = ejercicios;
    }
}

