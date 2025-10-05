package com.acme.inventory.src.infraestructure.jsonapi;

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
                Map<String, Object> attributes,
                /**
                 * las relaciones del recurso
                 */
                Map<String, Object> relationships,
                /**
                 * los enlaces del recurso
                 */
                Map<String, String> links) {
}