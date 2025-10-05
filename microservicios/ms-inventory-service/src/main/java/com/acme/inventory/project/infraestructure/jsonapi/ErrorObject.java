package com.acme.inventory.project.infraestructure.jsonapi;

/**
 * un objeto de error
 */
public record ErrorObject(
        /**
         * el código de error
         */
        String status,
        /**
         * el título del error
         */
        String title,
        /**
         * los detalles del error
         */
        String detail) {
}