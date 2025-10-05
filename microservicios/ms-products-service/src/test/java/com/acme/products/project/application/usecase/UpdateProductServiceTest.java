package com.acme.products.project.application.usecase;

import com.acme.products.project.application.port.out.LoadProduct;
import com.acme.products.project.application.port.out.SaveProduct;
import com.acme.products.project.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateProductServiceTest {

    LoadProduct load;
    SaveProduct save;
    UpdateProductService svc;

    @BeforeEach
    void setUp() {
        load = mock(LoadProduct.class);
        save = mock(SaveProduct.class);
        svc = new UpdateProductService(load, save);
    }

    @Test
    void update_updates_and_saves() {
        var now = Instant.now();
        var existing = new Product("1", "old", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        when(load.findById("1")).thenReturn(Optional.of(existing));
        when(save.save(any())).thenAnswer(i -> i.getArgument(0));
        var updated = svc.update("1", "new", BigDecimal.TEN, "EUR", "INACTIVE");
        assertEquals("new", updated.getName());
        assertEquals(BigDecimal.TEN, updated.getPrice());
        assertEquals("EUR", updated.getCurrency());
        assertEquals("INACTIVE", updated.getStatus());
        verify(save).save(any());
    }

    @Test
    void update_nonexistent_throws() {
        when(load.findById("x")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> svc.update("x", null, null, null, null));
    }
}
