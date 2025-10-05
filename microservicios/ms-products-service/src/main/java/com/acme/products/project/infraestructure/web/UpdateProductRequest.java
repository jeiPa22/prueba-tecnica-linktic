package com.acme.products.project.infraestructure.web;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Representa una solicitud para actualizar un producto.
 * Contiene los campos necesarios para actualizar la información
 * de un producto existente.
 */
public record UpdateProductRequest(
        /**
         * Nombre del producto
         */
        String name,
        /**
         * Descripción del producto
         */
        @DecimalMin(value = "0.0", inclusive = true) BigDecimal price,
        /**
         * Moneda del precio (código ISO 4217 de 3 letras)
         */
        @Size(min = 3, max = 3) String currency,
        /**
         * Estado del producto (activo/inactivo)
         */
        String status) {
}