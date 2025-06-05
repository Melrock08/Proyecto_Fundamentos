package com.example.entrenamiento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.entrenamiento.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioService usuarioService;

    public SecurityConfig(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * 1) Exponemos el AuthenticationManager para autenticaciones internas.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig
    ) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * 2) Definimos el SecurityFilterChain:
     *    - /login.html, /register.html, /styles/**, /images/**, /h2-console/** quedan libres.
     *    - /home.html (y cualquier otra URL) requiere autenticación.
     *    - Indicamos que el loginPage (GET) es /login.html.
     *    - El POST a /login lo maneja Spring Security automáticamente.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF deshabilitado para desarrollo; en producción conviene configurarlo.
            .csrf(csrf -> csrf.disable())

            // 1) Rutas públicas (sin autenticación)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/login.html",
                    "/login",             // añade para login GET/POST
                    "/register.html",
                    "/home.html",
                    "/register", 
                    "/styles/**",
                    "/images/**",
                    "/h2-console/**",
                    "/uploads/**" 
                ).permitAll()
                .requestMatchers(
                        "/api/me",
                        "/api/me/photo"
                    ).authenticated()
                .anyRequest().authenticated()
            )

            // 2) Login por formulario
            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/login")         // que coincida con el action del <form>
               // Aquí va el successHandler que se fija en el rol
                .successHandler((request, response, authentication) -> {
                    // Extraemos roles del usuario autenticado
                    boolean isEntrenador = authentication.getAuthorities().stream()
                            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ENTRENADOR"));
                    boolean isDeportista = authentication.getAuthorities().stream()
                            .anyMatch(auth -> auth.getAuthority().equals("ROLE_DEPORTISTA"));

                    if (isEntrenador) {
                        response.sendRedirect("/DSentrenador.html");
                    } else if (isDeportista) {
                        response.sendRedirect("/DSdeportista.html");
                    } else {
                        // Usuario sin rol específico (por si en el futuro se agrega otro rol)
                        response.sendRedirect("/home.html");
                    }
                })
                .permitAll()
            )

            // 3) Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html?logout")
                .permitAll()
            )

            // 4) Usamos nuestro UsuarioService para cargar usuarios
            .userDetailsService(usuarioService);

        // Permitir que la consola H2 se muestre en un iframe “same origin”
        http.headers(headers -> headers
            .frameOptions(frame -> frame.sameOrigin())
        );

        return http.build();
    }
}
