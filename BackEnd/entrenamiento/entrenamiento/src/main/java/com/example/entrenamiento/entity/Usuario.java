package com.example.entrenamiento.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Correo electrónico (único, no nulo).
    @Column(nullable = false, unique = true)
    private String email;

    // Nombre completo
    @Column(nullable = false)
    private String nombre;

    // Edad
    @Column(nullable = false)
    private Integer edad;

    // Nombre de usuario (único, no nulo)
    @Column(nullable = false, unique = true)
    private String username;

    // Contraseña (no nulo)
    @Column(nullable = false)
    private String password;

    // Rol (por ejemplo: "ROLE_ENTRENADOR" o "ROLE_DEPORTISTA")
    @Column(nullable = false)
    private String role;

    @Column(name = "photo_filename")
    private String photoFilename;

    public Usuario() { }

    public Usuario(String email, String nombre, Integer edad, String username, String password, String role) {
        this.email = email;
        this.nombre = nombre;
        this.edad = edad;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // — Getters y Setters — //

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getPhotoFilename() {
        return photoFilename;
    }
    public void setPhotoFilename(String photoFilename) {
        this.photoFilename = photoFilename;
    }
}
