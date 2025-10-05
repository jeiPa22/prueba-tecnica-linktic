package com.acme.products.project.infraestructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuracion de seguridad para el servicio de productos.
 */
@ConfigurationProperties("products.security")
public record ProductsSecurityProperties(
        /**
         * Cabecera HTTP que contiene la clave de API.
         */
        String header,
        /**
         * Clave de API esperada para autenticar las solicitudes.
         */
        String expectedKey) {
}