package com.acme.products.shared.error;

import com.acme.products.project.infraestructure.jsonapi.ErrorDocument;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleBadRequest_returns_400_with_error() {
        var ex = new IllegalArgumentException("bad thing");
        ResponseEntity<ErrorDocument> r = handler.handleBadRequest(ex);
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
        assertNotNull(r.getBody());
        assertFalse(r.getBody().errors().isEmpty());
        assertEquals("400", r.getBody().errors().get(0).status());
    }

    @Test
    void handleValidation_returns_422() {
        var ex = mock(MethodArgumentNotValidException.class);
        ResponseEntity<ErrorDocument> r = handler.handleValidation(ex);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, r.getStatusCode());
        assertNotNull(r.getBody());
    }

    @Test
    void handleGeneric_returns_500() {
        var ex = new RuntimeException("boom");
        ResponseEntity<ErrorDocument> r = handler.handleGeneric(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, r.getStatusCode());
        assertNotNull(r.getBody());
    }
}
