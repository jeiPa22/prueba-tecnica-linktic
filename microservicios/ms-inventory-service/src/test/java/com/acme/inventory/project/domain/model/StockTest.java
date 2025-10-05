package com.acme.inventory.project.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StockTest {

    @Test
    void constructor_setsFields() {
        Instant now = Instant.now();
        Stock s = new Stock("p-1", 5, now);

        assertThat(s.getProductId()).isEqualTo("p-1");
        assertThat(s.getQuantity()).isEqualTo(5);
        assertThat(s.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void constructor_negativeQuantity_throws() {
        Instant now = Instant.now();
        assertThrows(IllegalArgumentException.class, () -> new Stock("p-1", -1, now));
    }

    @Test
    void constructor_nulls_throw() {
        Instant now = Instant.now();
        assertThrows(NullPointerException.class, () -> new Stock(null, 1, now));
        assertThrows(NullPointerException.class, () -> new Stock("p-1", 1, null));
    }

    @Test
    void withQuantity_returnsNewStockWithUpdatedTime() {
        Instant now = Instant.now();
        Stock s = new Stock("p-2", 3, now);

        Stock s2 = s.withQuantity(10);

        assertThat(s2).isNotNull();
        assertThat(s2.getProductId()).isEqualTo(s.getProductId());
        assertThat(s2.getQuantity()).isEqualTo(10);
        assertThat(s2.getUpdatedAt()).isAfter(s.getUpdatedAt());
    }

    @Test
    void withQuantity_negative_throws() {
        Instant now = Instant.now();
        Stock s = new Stock("p-3", 2, now);
        assertThrows(IllegalArgumentException.class, () -> s.withQuantity(-5));
    }
}
