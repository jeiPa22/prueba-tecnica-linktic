package com.acme.products.project.infraestructure.security;

// ... no unused imports
import org.junit.jupiter.api.Test;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    @Test
    void corsConfigurationSource_registers_api_pattern() {
        var cfg = new SecurityConfig();
    var source = (UrlBasedCorsConfigurationSource) cfg.corsConfigurationSource();

        var configs = source.getCorsConfigurations();
        // expect the /api/** pattern to be registered
        assertTrue(configs.keySet().stream().anyMatch(k -> k.contains("/api/")));
        var c = configs.values().stream().findFirst().orElse(null);
        assertNotNull(c);
        var origins = c.getAllowedOrigins();
        var methods = c.getAllowedMethods();
        var headers = c.getAllowedHeaders();
        assertNotNull(origins);
        assertNotNull(methods);
        assertNotNull(headers);
        assertFalse(origins.isEmpty());
        assertFalse(methods.isEmpty());
        assertFalse(headers.isEmpty());
    }
}
