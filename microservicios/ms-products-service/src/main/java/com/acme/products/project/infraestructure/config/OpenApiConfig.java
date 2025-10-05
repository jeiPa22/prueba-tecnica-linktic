package com.acme.products.project.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Configuración de OpenAPI para la documentación de la API.
 */
@Configuration
public class OpenApiConfig {
    /**
     * Configura la documentación de OpenAPI.
     * Define la información básica de la API y el esquema de seguridad basado en
     * clave API.
     *
     * @return La configuración de OpenAPI
     */
    @Bean
    OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title("Products API").version("v1"))
                .components(new Components().addSecuritySchemes("ApiKeyAuth",
                        new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER)
                                .name("X-API-KEY")))
                .addSecurityItem(new SecurityRequirement().addList("ApiKeyAuth"));
    }
}