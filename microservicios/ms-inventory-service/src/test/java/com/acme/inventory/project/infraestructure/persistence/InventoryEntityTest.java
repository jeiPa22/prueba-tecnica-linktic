package com.acme.inventory.project.infraestructure.persistence;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class InventoryEntityTest {

    @Test
    void getters_and_setters_and_equals() {
        var now = Instant.now();
        var e = new InventoryEntity("x", 2, now);
        assertThat(e.getProductId()).isEqualTo("x");
        assertThat(e.getQuantity()).isEqualTo(2);

        e.setQuantity(5);
        e.setUpdatedAt(Instant.now());
        assertThat(e.getQuantity()).isEqualTo(5);

        var e2 = new InventoryEntity("x", 7, now);
        assertThat(e).isEqualTo(e2);
        assertThat(e.hashCode()).isEqualTo(e2.hashCode());
    }
}
