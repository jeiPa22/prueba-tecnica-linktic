package com.acme.inventory.project.infraestructure.jsonapi;

import java.util.List;
import java.util.Map;

/**
 * un documento de datos
 */
public record DataDocument(
        /**
         * los enlaces del documento
         */
        Map<String, String> links,
        /**
         * los datos del documento
         */
        Object data,
        /**
         * los recursos incluidos en el documento
         */
        List<Object> included) {
}