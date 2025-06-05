// src/main/java/com/example/entrenamiento/dto/UsuarioDTO.java
package com.example.entrenamiento.dto;

/**
 * DTO para exponer solo los datos necesarios del Usuario (sin contraseña).
 */
public class UsuarioDTO {
    private String email;
    private String nombre;
    private Integer edad;
    private String username;
    private String role;
    private String photoFilename;

    public UsuarioDTO() {
        // Constructor vacío (para frameworks que lo requieran)
    }

    public UsuarioDTO(String email, String nombre, Integer edad, String username, String role, String photoFilename) {
        this.email = email;
        this.nombre = nombre;
        this.edad = edad;
        this.username = username;
        this.role = role;
        this.photoFilename = photoFilename;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
      
    public String getPhotoFilename() { 
        return photoFilename; 
    }


    // Setters (opcionales, si en algún momento quieres serializar/deserializar)
    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhotoFilename(String photoFilename) { 
        this.photoFilename = photoFilename;
    }
}
