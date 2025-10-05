package com.acme.products.project.application.usecase;

import com.acme.products.project.application.port.out.LoadProduct;
import com.acme.products.project.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProductServiceTest {

    LoadProduct load;
    GetProductService svc;

    @BeforeEach
    void setUp() {
        load = mock(LoadProduct.class);
        svc = new GetProductService(load);
    }

    @Test
    void byId_returns_product_or_throws() {
        var now = Instant.now();
        var p = new Product("1", "n", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        when(load.findById("1")).thenReturn(Optional.of(p));
        var out = svc.byId("1");
        assertEquals("1", out.getId());

        when(load.findById("2")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> svc.byId("2"));
    }
}
