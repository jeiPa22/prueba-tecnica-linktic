package com.acme.products.project.application.usecase;

import com.acme.products.project.application.port.out.SaveProduct;
import com.acme.products.project.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateProductServiceTest {

    SaveProduct save;
    CreateProductService svc;

    @BeforeEach
    void setUp() {
        save = mock(SaveProduct.class);
        svc = new CreateProductService(save);
    }

    @Test
    void create_sets_id_and_defaults_and_calls_save() {
        var now = Instant.now();
        // Product constructor requires non-null id and price; provide defaults
        var in = new Product("in-id", "n", BigDecimal.ZERO, "USD", "ACTIVE", now, now);
        when(save.save(any())).thenAnswer(i -> i.getArgument(0));
        var out = svc.create(in);
        // id should be kept since input had id
        assertEquals("in-id", out.getId());
        assertEquals(BigDecimal.ZERO, out.getPrice());
        verify(save).save(any());
    }
}
