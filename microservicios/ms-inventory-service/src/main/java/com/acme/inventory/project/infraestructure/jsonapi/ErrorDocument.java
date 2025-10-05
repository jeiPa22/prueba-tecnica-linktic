package com.acme.inventory.project.infraestructure.jsonapi;

import java.util.List;

/**
 * un documento de error
 */
public record ErrorDocument(List<ErrorObject> errors) {
}