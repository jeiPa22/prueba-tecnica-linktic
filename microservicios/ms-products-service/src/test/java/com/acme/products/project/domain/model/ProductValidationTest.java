package com.acme.products.project.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ProductValidationTest {

    @Test
    void constructor_rejectsInvalidCurrency() {
        assertThrows(IllegalArgumentException.class, () -> new Product("1","n",BigDecimal.ONE,"US","ACTIVE",Instant.now(),Instant.now()));
    }

    @Test
    void constructor_rejectsMissingStatus() {
        assertThrows(IllegalArgumentException.class, () -> new Product("1","n",BigDecimal.ONE,"USD",null,Instant.now(),Instant.now()));
    }

    @Test
    void withUpdated_keepsExistingWhenNulls() {
        Product p = new Product("1","n",BigDecimal.ONE,"USD","ACTIVE",Instant.now(),Instant.now());
        Product u = p.withUpdated(null, null, null, null);
        assertEquals(p.getId(), u.getId());
        assertEquals(p.getName(), u.getName());
    }
}
