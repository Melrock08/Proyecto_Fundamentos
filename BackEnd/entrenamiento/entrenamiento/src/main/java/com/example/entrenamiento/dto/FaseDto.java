package com.example.entrenamiento.dto;

import java.util.List;

public class FaseDto {
    private String nombre;
    private List<SemanaDto> semanas;

    // GETTERS
    public String getNombre() {
        return nombre;
    }

    public List<SemanaDto> getSemanas() {
        return semanas;
    }

    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSemanas(List<SemanaDto> semanas) {
        this.semanas = semanas;
    }
}
