package com.example.entrenamiento.dto;

import java.util.List;


public class PlanDto {
    private String nombre;
    private String descripcion;
    private String nivel;
    private int duracion;
    private String objetivoGeneral;
    private String publico;
    private List<FaseDto> fases;

    // GETTERS
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getNivel() { return nivel; }
    public int getDuracion() { return duracion; }
    public String getObjetivoGeneral() { return objetivoGeneral; }
    public String getPublico() { return publico; }
    public List<FaseDto> getFases() { return fases; }


    public void setNombre(String nombre) {
    this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    public void setPublico(String publico) {
        this.publico = publico;
    }

    public void setFases(List<FaseDto> fases) {
        this.fases = fases;
    }
    
    
    }

