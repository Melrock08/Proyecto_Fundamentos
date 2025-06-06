package com.example.entrenamiento.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "planes")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // — Campos básicos existentes:
    private String nombre;

    // — Metadatos para “plan personalizado”:
    private String descripcion;        // descripción breve del plan
    private String nivel;              // principiante, intermedio, avanzado
    private Integer duracion;          // en semanas
    private String objetivoGeneral;    // “perder_grasa”, “ganar_musculo”, etc.
    private String publico;            // “hombres”, “mujeres”, “unisex”, etc.

    // — Relación con Fase (1 Plan → * Fases) —
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Fase> fases = new ArrayList<>();

    // — Relación ManyToMany con Usuario (planes suscritos) —
    @ManyToMany
    @JoinTable(
      name = "usuario_plan",
      joinColumns = @JoinColumn(name = "plan_id"),
      inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> usuariosSuscritos = new HashSet<>();

    // —— Constructores, getters y setters —— //

    public Plan() { }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return nivel;
    }
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Integer getDuracion() {
        return duracion;
    }
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }
    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    public String getPublico() {
        return publico;
    }
    public void setPublico(String publico) {
        this.publico = publico;
    }

    public List<Fase> getFases() {
        return fases;
    }
    public void setFases(List<Fase> fases) {
        this.fases = fases;
    }

    public Set<Usuario> getUsuariosSuscritos() {
        return usuariosSuscritos;
    }
    public void setUsuariosSuscritos(Set<Usuario> usuariosSuscritos) {
        this.usuariosSuscritos = usuariosSuscritos;
    }

    // (Opcional) método auxiliar para agregar usuario suscrito:
    public void agregarUsuarioSuscrito(Usuario usuario) {
        this.usuariosSuscritos.add(usuario);
        usuario.getPlanesSuscritos().add(this);
    }

    // (Opcional) método auxiliar para remover usuario suscrito:
    public void removerUsuarioSuscrito(Usuario usuario) {
        this.usuariosSuscritos.remove(usuario);
        usuario.getPlanesSuscritos().remove(this);
    }
}
