package com.acme.inventory.shared.error;

import com.acme.inventory.project.infraestructure.jsonapi.ErrorDocument;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleBadRequest_mapsIllegalArgument() {
        var ex = new IllegalArgumentException("bad thing");
        ResponseEntity<ErrorDocument> r = handler.handleBadRequest(ex);
        assertThat(r.getStatusCodeValue()).isEqualTo(400);
        assertThat(r.getBody()).isNotNull();
    }

    @Test
    void handleConflict_mapsIllegalState() {
        var ex = new IllegalStateException("conflict");
        var r = handler.handleConflict(ex);
        assertThat(r.getStatusCodeValue()).isEqualTo(409);
    }

    @Test
    void handleValidation_maps422() {
        try {
            var method = this.getClass().getDeclaredMethod("dummyMethod", String.class);
            var mp = new org.springframework.core.MethodParameter(method, 0);
            var br = new org.springframework.validation.BeanPropertyBindingResult(new Object(), "obj");
            var ex = new MethodArgumentNotValidException(mp, br);
            var r = handler.handleValidation(ex);
            assertThat(r.getStatusCodeValue()).isEqualTo(422);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void handleGeneric_maps500() {
        var ex = new RuntimeException("boom");
        var r = handler.handleGeneric(ex);
        assertThat(r.getStatusCodeValue()).isEqualTo(500);
    }

    @SuppressWarnings("unused")
    private void dummyMethod(String p) {
    }
}
