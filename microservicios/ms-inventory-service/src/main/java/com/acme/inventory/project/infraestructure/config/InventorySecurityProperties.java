package com.acme.inventory.project.infraestructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades de seguridad para el servicio de inventario.
 *
 * @param header      El nombre del encabezado HTTP que contiene la clave de
 *                    seguridad.
 * @param expectedKey La clave de seguridad esperada para autenticar las
 *                    solicitudes.
 */
@ConfigurationProperties("inventory.security")
public record InventorySecurityProperties(
        /**
         * El nombre del encabezado HTTP que contiene la clave de seguridad.
         */
        String header,
        /**
         * La clave de seguridad esperada para autenticar las solicitudes.
         */
        String expectedKey) {
}
