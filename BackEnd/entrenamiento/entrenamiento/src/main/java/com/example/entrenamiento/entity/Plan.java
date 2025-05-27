package com.example.entrenamiento.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "planes")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer edad;
    private String sexo;
    private Integer estatura;      // en centímetros
    private Integer peso;          // en kilogramos
    private Integer duracion;      // número de semanas
    private Integer frecuencia;    // días por semana
    private String intensidad;     // baja, media, alta
    private String objetivo;       // perder_peso, ganar_musculo…
    private String actividad;      // sedentario, moderado, alto
    private LocalDate fechaInicio;

    @Column(length = 500)
    private String notas;

    // —— Getters y setters ——
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public Integer getEstatura() { return estatura; }
    public void setEstatura(Integer estatura) { this.estatura = estatura; }

    public Integer getPeso() { return peso; }
    public void setPeso(Integer peso) { this.peso = peso; }

    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    public Integer getFrecuencia() { return frecuencia; }
    public void setFrecuencia(Integer frecuencia) { this.frecuencia = frecuencia; }

    public String getIntensidad() { return intensidad; }
    public void setIntensidad(String intensidad) { this.intensidad = intensidad; }

    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

    public String getActividad() { return actividad; }
    public void setActividad(String actividad) { this.actividad = actividad; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}
