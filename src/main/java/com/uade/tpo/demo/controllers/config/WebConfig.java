package com.uade.tpo.demo.controllers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir todos los endpoints
                .allowedOrigins("http://localhost:5173") // Origen de tu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowedHeaders("*") // Permitir cualquier cabecera
                .allowCredentials(true); // Si es necesario, habilitar el envío de cookies
    }
}
