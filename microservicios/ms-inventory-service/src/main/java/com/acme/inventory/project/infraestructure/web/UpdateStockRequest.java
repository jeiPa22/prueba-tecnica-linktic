package com.acme.inventory.project.infraestructure.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Representa una solicitud para actualizar el stock de un producto en el
 * sistema de inventario.
 *
 * @param productId Identificador del producto cuyo stock se va a actualizar. No
 *                  puede ser nulo ni vacío.
 * @param quantity  Cantidad a actualizar en el stock del producto. Debe ser un
 *                  valor no negativo.
 */
public record UpdateStockRequest(
        /**
         * Identificador del producto cuyo stock se va a actualizar.
         */
        @NotBlank String productId,
        /**
         * Cantidad a actualizar en el stock del producto. Debe ser un valor no
         * negativo.
         */
        @Min(0) int quantity) {
}