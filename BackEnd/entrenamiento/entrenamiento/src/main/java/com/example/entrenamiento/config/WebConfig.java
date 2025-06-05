package com.example.entrenamiento.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Exponer el directorio 'uploads/' para que sea accesible bajo la ruta '/uploads/**'
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // "file:uploads/" significa que lo toma del sistema de archivos en la carpeta uploads/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
