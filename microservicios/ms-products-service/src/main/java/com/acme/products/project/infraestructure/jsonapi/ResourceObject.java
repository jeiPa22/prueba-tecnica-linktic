package com.acme.products.project.infraestructure.jsonapi;

import java.util.Map;

/**
 * Representa un objeto de recurso en formato JSON:API.
 * Un objeto de recurso contiene un tipo, un ID, atributos,
 * relaciones y enlaces relacionados con el recurso.
 */
public record ResourceObject(
        /**
         * Tipo del recurso
         * 
         */
        String type,
        /**
         * ID del recurso
         * 
         */
        String id,
        /**
         * Atributos del recurso
         * 
         */
        Map<String, Object> attributes,
        /**
         * Relaciones del recurso
         * 
         */
        Map<String, RelationshipObject> relationships,
        /**
         * Enlaces relacionados con el recurso
         * 
         */
        Map<String, String> links) {
}