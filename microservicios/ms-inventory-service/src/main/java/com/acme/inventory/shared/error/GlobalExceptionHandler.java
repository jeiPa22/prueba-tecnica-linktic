package com.acme.inventory.shared.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.acme.inventory.src.infraestructure.jsonapi.ErrorDocument;
import com.acme.inventory.src.infraestructure.jsonapi.ErrorObject;

import java.util.List;

/**
 * Clase que maneja las excepciones globalmente en la aplicación
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de tipo IllegalArgumentException y devuelve una
     * respuesta
     * 
     * @param ex la excepción capturada
     * @return una ResponseEntity con el ErrorDocument y el estado HTTP 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDocument> handleBadRequest(IllegalArgumentException ex) {
        var doc = new ErrorDocument(List.of(new ErrorObject("400", "Petición incorrecta", ex.getMessage())));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(doc);
    }

    /**
     * Maneja las excepciones de tipo IllegalStateException y devuelve una respuesta
     * 
     * @param ex la excepción capturada
     * @return una ResponseEntity con el ErrorDocument y el estado HTTP 409
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDocument> handleConflict(IllegalStateException ex) {
        var doc = new ErrorDocument(List.of(new ErrorObject("409", "Conflictos", ex.getMessage())));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(doc);
    }

    /**
     * Maneja las excepciones de tipo MethodArgumentNotValidException y devuelve una
     * respuesta
     * 
     * @param ex la excepción capturada
     * @return una ResponseEntity con el ErrorDocument y el estado HTTP 422
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDocument> handleValidation(MethodArgumentNotValidException ex) {
        var doc = new ErrorDocument(
                List.of(new ErrorObject("422", "Entidad no procesable", "Falló la validación de la entidad")));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(doc);
    }

    /**
     * Maneja las excepciones genéricas y devuelve una respuesta
     * 
     * @param ex la excepción capturada
     * @return una ResponseEntity con el ErrorDocument y el estado HTTP 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDocument> handleGeneric(Exception ex) {
        var doc = new ErrorDocument(List.of(new ErrorObject("500", "Error interno del servidor", "Error inesperado")));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(doc);
    }
}
