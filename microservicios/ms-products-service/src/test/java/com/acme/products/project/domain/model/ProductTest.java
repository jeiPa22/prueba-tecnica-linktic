package com.acme.products.project.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void constructor_valid_and_getters() {
        var now = Instant.now();
        var p = new Product("id", "name", BigDecimal.TEN, "usd", "active", now, now);
        assertEquals("id", p.getId());
        assertEquals("name", p.getName());
        assertEquals(BigDecimal.TEN, p.getPrice());
        assertEquals("USD", p.getCurrency());
        assertEquals("ACTIVE", p.getStatus());
        assertEquals(now, p.getCreatedAt());
        assertEquals(now, p.getUpdatedAt());
    }

    @Test
    void constructor_invalid_name_throws() {
        var now = Instant.now();
        assertThrows(IllegalArgumentException.class, () -> new Product("id", "", BigDecimal.TEN, "USD", "ACTIVE", now, now));
    }

    @Test
    void constructor_invalid_price_throws() {
        var now = Instant.now();
        assertThrows(IllegalArgumentException.class, () -> new Product("id", "name", new BigDecimal("-1"), "USD", "ACTIVE", now, now));
    }

    @Test
    void constructor_invalid_currency_throws() {
        // currency must be 3 chars
        assertThrows(IllegalArgumentException.class, () -> new Product("id", "n", BigDecimal.ONE, "US", "ACTIVE", Instant.now(), Instant.now()));
    }

    @Test
    void constructor_missing_status_throws() {
        assertThrows(IllegalArgumentException.class, () -> new Product("id", "n", BigDecimal.ONE, "USD", "", Instant.now(), Instant.now()));
    }

    @Test
    void withUpdated_keeps_and_changes_fields() {
        var now = Instant.now();
        var p = new Product("id", "name", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        var p2 = p.withUpdated(null, null, null, null);
        assertEquals(p.getName(), p2.getName());
        assertEquals(p.getPrice(), p2.getPrice());

        var p3 = p.withUpdated("nn", BigDecimal.TEN, "EUR", "INACTIVE");
        assertEquals("nn", p3.getName());
        assertEquals(BigDecimal.TEN, p3.getPrice());
        assertEquals("EUR", p3.getCurrency());
        assertEquals("INACTIVE", p3.getStatus());
    }
    @Test
    void withUpdated_changes_fields_and_updates_time() throws InterruptedException {
        var now = Instant.now();
        var p = new Product("id", "name", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        Thread.sleep(5);
        var p2 = p.withUpdated("newName", BigDecimal.TEN, "eur", "inactive");
        assertEquals("id", p2.getId());
        assertEquals("newName", p2.getName());
        assertEquals(BigDecimal.TEN, p2.getPrice());
        assertEquals("EUR", p2.getCurrency());
        assertEquals("INACTIVE", p2.getStatus());
        assertEquals(p.getCreatedAt(), p2.getCreatedAt());
        assertTrue(p2.getUpdatedAt().isAfter(p.getUpdatedAt()));
    }
}
