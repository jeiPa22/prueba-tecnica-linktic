package com.acme.inventory.project.infraestructure.jsonapi;

import java.util.Map;

/**
 * un objeto de recurso
 */
public record ResourceObject(
        /**
         * el tipo de recurso
         */
        String type,
        /**
         * el id del recurso
         */
        String id,
        /**
         * los atributos del recurso
         */
        Map<String, ?> attributes,
        /**
         * las relaciones del recurso
         */
        Map<String, ?> relationships,
        /**
         * los enlaces del recurso
         */
        Map<String, String> links) {
}