package com.acme.products.project.infraestructure.persistence;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntitySettersTest {

    @Test
    void setters_and_getters_work() {
        var now = Instant.now();
        var e = new ProductEntity("id", "n", BigDecimal.TEN, "USD", "ACTIVE", now, now);

        e.setName("nn");
        e.setPrice(BigDecimal.ONE);
        e.setCurrency("EUR");
        e.setStatus("INACTIVE");
        e.setCreatedAt(now);
        e.setUpdatedAt(now);

        assertEquals("nn", e.getName());
        assertEquals(BigDecimal.ONE, e.getPrice());
        assertEquals("EUR", e.getCurrency());
        assertEquals("INACTIVE", e.getStatus());
        assertEquals(now, e.getCreatedAt());
        assertEquals(now, e.getUpdatedAt());
    }
}
