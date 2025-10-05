package com.acme.inventory.src.infraestructure.jsonapi;

import java.util.List;

/**
 * un documento de error
 */
public record ErrorDocument(List<ErrorObject> errors) {
}