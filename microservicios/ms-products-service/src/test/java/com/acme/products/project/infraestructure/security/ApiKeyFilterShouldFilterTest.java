package com.acme.products.project.infraestructure.security;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApiKeyFilterShouldFilterTest {

    @Test
    void shouldNotFilter_returns_false_for_api_paths() {
        var f = new ApiKeyFilter("X-API-KEY", "k");
        var req = mock(HttpServletRequest.class);
        when(req.getRequestURI()).thenReturn("/api/v1/products");
        assertFalse(f.shouldNotFilter(req));
    }
}
