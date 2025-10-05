package com.acme.inventory.project.infraestructure.persistence;

import com.acme.inventory.project.domain.model.Stock;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class InventoryMapperTest {

    @Test
    void toEntity_and_toDomain_roundtrip() {
        var now = Instant.now();
        var s = new Stock("p1", 4, now);
        var e = InventoryMapper.toEntity(s);

        assertThat(e.getProductId()).isEqualTo(s.getProductId());
        assertThat(e.getQuantity()).isEqualTo(s.getQuantity());

        var back = InventoryMapper.toDomain(e);
        assertThat(back.getProductId()).isEqualTo(s.getProductId());
        assertThat(back.getQuantity()).isEqualTo(s.getQuantity());
    }

    @Test
    void upsertFromDomain_updatesFields() {
        var now = Instant.now();
        var e = new InventoryEntity("p2", 1, now);
        var s = new Stock("p2", 9, now);

        var updated = InventoryMapper.upsertFromDomain(e, s);
        assertThat(updated.getQuantity()).isEqualTo(9);
        assertThat(updated.getUpdatedAt()).isNotNull();
    }
}
