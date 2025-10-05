package com.acme.products.project.infraestructure.jsonapi;

import java.util.List;

/**
 * Representa un documento de error en formato JSON:API.
 * Un documento de error contiene una lista de objetos de error
 * que describen los errores ocurridos durante el procesamiento
 * de una solicitud.
 */
public record ErrorDocument(
        /**
         * Lista de objetos de error
         * 
         */
        List<ErrorObject> errors) {
}