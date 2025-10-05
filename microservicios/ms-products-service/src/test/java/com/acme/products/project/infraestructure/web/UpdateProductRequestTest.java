package com.acme.products.project.infraestructure.web;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UpdateProductRequestTest {

    @Test
    void record_holds_values() {
        var r = new UpdateProductRequest("n", BigDecimal.TEN, "USD", "ACTIVE");
        assertEquals("n", r.name());
        assertEquals(BigDecimal.TEN, r.price());
        assertEquals("USD", r.currency());
        assertEquals("ACTIVE", r.status());
    }
}
