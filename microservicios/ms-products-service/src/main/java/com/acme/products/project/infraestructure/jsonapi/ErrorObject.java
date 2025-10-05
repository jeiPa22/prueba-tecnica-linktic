package com.acme.products.project.infraestructure.jsonapi;

/**
 * Representa un objeto de error en formato JSON:API.
 * Un objeto de error contiene información sobre un error
 * ocurrido durante el procesamiento de una solicitud.
 */
public record ErrorObject(
        /**
         * Estado del error
         * 
         */
        String status,
        /**
         * Código del error
         * 
         */
        String title,
        /**
         * Detalle del error
         * 
         */
        String detail) {
}