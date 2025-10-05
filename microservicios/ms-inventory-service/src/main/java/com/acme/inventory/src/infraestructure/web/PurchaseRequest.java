package com.acme.inventory.src.infraestructure.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Representa una solicitud de compra de un producto en el sistema de
 * inventario.
 *
 * @param productId Identificador del producto que se va a comprar. No puede ser
 *                  nulo ni vacío.
 * @param units     Número de unidades del producto que se van a comprar. Debe
 *                  ser
 *                  un valor positivo.
 */
public record PurchaseRequest(
        /**
         * Identificador del producto que se va a comprar.
         */
        @NotBlank String productId,
        /**
         * Número de unidades del producto que se van a comprar. Debe ser un valor
         * positivo.
         */
        @Min(1) int units) {
}