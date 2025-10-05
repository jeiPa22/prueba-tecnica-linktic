package com.acme.products.project.infraestructure.jsonapi;

import java.util.Map;

/**
 * Representa un objeto de relación en formato JSON:API.
 * Un objeto de relación contiene enlaces y datos relacionados con la relación.
 */
public record RelationshipObject(
        /**
         * Enlaces relacionados con la relación
         * 
         */
        Map<String, String> links,

        /**
         * Datos relacionados con la relación
         * 
         */
        Map<String, String> data) {
}