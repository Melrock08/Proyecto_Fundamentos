package com.example.entrenamiento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    
    /** Mostrar formulario de login (Spring Security lo procesará automáticamente) */
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; 
    }

    /** Página de inicio tras login exitoso */
    @GetMapping("/home")
    public String homeAutenticado() {
        return "home"; 
    }
}

