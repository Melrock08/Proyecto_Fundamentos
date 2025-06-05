package com.example.entrenamiento.controller;

import com.example.entrenamiento.dto.UsuarioDTO;
import com.example.entrenamiento.entity.Usuario;
import com.example.entrenamiento.repository.UsuarioRepository;
import com.example.entrenamiento.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    private static final Logger log = LoggerFactory.getLogger(AuthRestController.class);

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepo;

    public AuthRestController(UsuarioService usuarioService,
                              UsuarioRepository usuarioRepo) {
        this.usuarioService = usuarioService;
        this.usuarioRepo = usuarioRepo;
    }

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
            @RequestParam String rol
    ) {
        if (usuarioService.usuarioExiste(username)) {
            String error = URLEncoder.encode("El nombre de usuario ya existe",
                                             StandardCharsets.UTF_8);
            return new RedirectView("/register.html?error=" + error);
        }
        if (usuarioService.emailExiste(email)) {
            String error = URLEncoder.encode("El correo ya está registrado",
                                             StandardCharsets.UTF_8);
            return new RedirectView("/register.html?error=" + error);
        }

        Usuario nuevo = new Usuario();
        nuevo.setEmail(email);
        nuevo.setNombre(nombre);
        nuevo.setEdad(edad);
        nuevo.setUsername(username);
        nuevo.setPassword(password);
        nuevo.setRole("ROLE_" + rol);
        usuarioService.registrarNuevoUsuario(nuevo);

        return new RedirectView("/login.html?registrado=true");
    }

    @GetMapping("/api/me")
    public UsuarioDTO obtenerUsuarioAutenticado(Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new UsuarioDTO(
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getEdad(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getPhotoFilename()
        );
    }

    @PostMapping(value = "/api/me/photo",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> subirFotoPerfil(
            Authentication authentication,
            @RequestParam("file") MultipartFile file
    ) {
        String username = authentication.getName();
        Optional<Usuario> optUsuario = usuarioRepo.findByUsername(username);
        if (optUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Usuario no autenticado");
        }

        Usuario usuario = optUsuario.get();

        try {
            String originalName = file.getOriginalFilename();
            String extension = "";
            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf('.'));
            }
            String filename = UUID.randomUUID().toString() + extension;

            File carpetaUploads = new File("uploads");
            if (!carpetaUploads.exists()) {
                boolean creada = carpetaUploads.mkdirs();
                if (!creada) {
                    log.error("No se pudo crear carpeta 'uploads/'");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                         .body("Error creando carpeta uploads");
                }
            }

            File destino = new File(carpetaUploads, filename);
            file.transferTo(destino);

            usuario.setPhotoFilename(filename);
            usuarioRepo.save(usuario);

            return ResponseEntity.ok("Foto subida correctamente");
        } catch (IOException ioe) {
            log.error("IOException al guardar la foto de perfil", ioe);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error guardando la foto: " + ioe.getMessage());
        } catch (RuntimeException re) {
            log.error("RuntimeException al procesar foto de perfil", re);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error en el servidor: " + re.getMessage());
        }
    }
}
