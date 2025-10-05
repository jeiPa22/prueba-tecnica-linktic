package com.acme.products.project.infraestructure.jsonapi;

import java.util.List;
import java.util.Map;

/**
 * Representa un documento de datos en formato JSON:API.
 * Un documento de datos contiene enlaces, datos, recursos incluidos
 * y metadatos relacionados con la respuesta.
 */
public record DataDocument(
        /**
         * Enlaces relacionados con el documento
         * 
         */
        Map<String, String> links,
        /**
         * Datos principales del documento
         * 
         */
        Object data,
        /**
         * Recursos incluidos en el documento
         * 
         */
        List<Object> included,
        /**
         * Metadatos relacionados con el documento
         * 
         */
        Map<String, Object> meta) {
}