package com.example.entrenamiento.controller;

import com.example.entrenamiento.dto.UsuarioDTO;
import com.example.entrenamiento.entity.Usuario;
import com.example.entrenamiento.repository.UsuarioRepository;
import com.example.entrenamiento.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthRestController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepo;

    public AuthRestController(UsuarioService usuarioService,
                              UsuarioRepository usuarioRepo) {
        this.usuarioService = usuarioService;
        this.usuarioRepo = usuarioRepo;
    }

    /** 
     * Mostrar formulario de registro 
     */
    @GetMapping("/register")
    public RedirectView mostrarRegistro() {
        return new RedirectView("/register.html");
    }

    /**
     * Procesar formulario de registro.
     */
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
     * (usa la clase UsuarioDTO que ya tienes en com.example.entrenamiento.dto).
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
                usuario.getPhotoFilename()  // Aquí photoFilename contendrá la URL si ya la guardó
        );
    }

    /**
     * POST /api/me/photo-url
     * Guarda en la base de datos la URL de la foto de perfil (en lugar de subir un archivo).
     * Recibe un JSON:
     *   { "photoUrl": "https://dominio.com/mi-foto.jpg" }
     */
    @PostMapping(value = "/api/me/photo-url", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> guardarUrlFoto(
            Authentication authentication,
            @RequestBody Map<String, String> payload
    ) {
        // 1) Validar que venga la clave "photoUrl" en el JSON
        String photoUrl = payload.get("photoUrl");
        if (photoUrl == null || photoUrl.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("No se recibió ninguna URL.");
        }
        photoUrl = photoUrl.trim();

        // 2) Validar que comience por "http://" o "https://"
        if (!photoUrl.startsWith("http://") && !photoUrl.startsWith("https://")) {
            return ResponseEntity.badRequest()
                    .body("La URL debe comenzar con http:// o https://");
        }

        // 3) Obtener el usuario autenticado
        String username = authentication.getName();
        Usuario usuario = usuarioRepo.findByUsername(username).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario no encontrado o sesión expirada.");
        }

        // 4) Guardar la URL en el campo photoFilename de la entidad Usuario
        usuario.setPhotoFilename(photoUrl);
        usuarioRepo.save(usuario);

        // 5) Responder 200 OK
        return ResponseEntity.ok("URL guardada correctamente.");
    }
}
