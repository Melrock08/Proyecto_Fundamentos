package com.example.entrenamiento.controller;

import com.example.entrenamiento.dto.UsuarioDTO;
import com.example.entrenamiento.entity.Usuario;
import com.example.entrenamiento.repository.UsuarioRepository;
import com.example.entrenamiento.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;


import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@RestController
public class AuthRestController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepo;

    public AuthRestController(UsuarioService usuarioService,
                              UsuarioRepository usuarioRepo) {
        this.usuarioService = usuarioService;
        this.usuarioRepo = usuarioRepo;
    }

    /** Mostrar formulario de registro */
    @GetMapping("/register")
    public RedirectView mostrarRegistro() {
        return new RedirectView("/register.html");
    }

    @PostMapping("/register")
    public RedirectView procesarRegistro(
            @RequestParam String email,
            @RequestParam String nombre,
            @RequestParam Integer edad,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String rol // "ENTRENADOR" o "DEPORTISTA"
    ) {
        // 1) Verificar duplicado de username
        if (usuarioService.usuarioExiste(username)) {
            String error = URLEncoder.encode(
                    "El nombre de usuario ya existe",
                    StandardCharsets.UTF_8
            );
            return new RedirectView("/register.html?error=" + error);
        }

        // 2) Verificar duplicado de email
        if (usuarioService.emailExiste(email)) {
            String error = URLEncoder.encode(
                    "El correo ya está registrado",
                    StandardCharsets.UTF_8
            );
            return new RedirectView("/register.html?error=" + error);
        }

        // 3) Crear y guardar nuevo usuario
        Usuario nuevo = new Usuario();
        nuevo.setEmail(email);
        nuevo.setNombre(nombre);
        nuevo.setEdad(edad);
        nuevo.setUsername(username);
        nuevo.setPassword(password);
        nuevo.setRole("ROLE_" + rol);
        usuarioService.registrarNuevoUsuario(nuevo);

        // 4) Redirigir a login.html?registrado=true
        return new RedirectView("/login.html?registrado=true");
    }

    /**
     * GET /api/me
     * Devuelve un JSON con los datos básicos del usuario autenticado
     * (usa la clase UsuarioDTO que ya tienes definida en com.example.entrenamiento.dto).
     */
    @GetMapping("/api/me")
    public UsuarioDTO obtenerUsuarioAutenticado(Authentication authentication) {
        String username = authentication.getName();
        Optional<Usuario> opt = usuarioRepo.findByUsername(username);
        if (opt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Usuario usuario = opt.get();

        // Construye el DTO usando tu clase existente
        return new UsuarioDTO(
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getEdad(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getPhotoFilename()
        );
    }

    @PostMapping(value = "/api/me/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void subirFotoPerfil(
            Authentication authentication,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String username = authentication.getName();
        Usuario usuario = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Generar nombre único con UUID
        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf('.'));
        }
        String filename = UUID.randomUUID().toString() + extension;

        // Guardar en carpeta "uploads/"
        File carpetaUploads = new File("uploads");
        if (!carpetaUploads.exists()) {
            carpetaUploads.mkdirs();
        }
        File destino = new File(carpetaUploads, filename);
        file.transferTo(destino);

        // Actualizar el usuario
        usuario.setPhotoFilename(filename);
        usuarioRepo.save(usuario);
    }
}
