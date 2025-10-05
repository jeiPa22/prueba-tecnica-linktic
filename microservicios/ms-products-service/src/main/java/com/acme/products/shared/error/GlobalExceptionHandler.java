package com.acme.products.shared.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.acme.products.project.infraestructure.jsonapi.ErrorDocument;
import com.acme.products.project.infraestructure.jsonapi.ErrorObject;

import java.util.List;

/**
 * Manejador global de excepciones para la aplicación.
 * Captura excepciones comunes y las convierte en respuestas JSON:API
 * con el formato adecuado.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo IllegalArgumentException
     * 
     * @param ex La excepción capturada
     * @return Respuesta HTTP con el documento de error
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDocument> handleBadRequest(IllegalArgumentException ex) {
        var doc = new ErrorDocument(List.of(new ErrorObject("400", "Bad Request", ex.getMessage())));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(doc);
    }

    /**
     * Maneja excepciones de tipo MethodArgumentNotValidException
     * 
     * @param ex La excepción capturada
     * @return Respuesta HTTP con el documento de error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDocument> handleValidation(MethodArgumentNotValidException ex) {
        var doc = new ErrorDocument(List.of(new ErrorObject("422", "Unprocessable Entity", "Validation failed")));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(doc);
    }

    /**
     * Maneja cualquier otra excepción no capturada específicamente
     * 
     * @param ex La excepción capturada
     * @return Respuesta HTTP con el documento de error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDocument> handleGeneric(Exception ex) {
        var doc = new ErrorDocument(List.of(new ErrorObject("500", "Internal Server Error", "Unexpected error")));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(doc);
    }
}