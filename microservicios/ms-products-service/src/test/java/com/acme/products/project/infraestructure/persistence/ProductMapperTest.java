package com.acme.products.project.infraestructure.persistence;

import com.acme.products.project.domain.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    void toEntity_and_back() {
        var now = Instant.now();
        var domain = new Product("id", "n", BigDecimal.ONE, "usd", "active", now, now);
        var entity = ProductMapper.toEntity(domain);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getPrice(), entity.getPrice());
        assertEquals(domain.getCurrency(), entity.getCurrency());

        var back = ProductMapper.toDomain(entity);
        assertEquals(domain.getId(), back.getId());
        assertEquals(domain.getName(), back.getName());
    }
}
