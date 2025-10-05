package com.acme.products.project.application.usecase;

import com.acme.products.project.application.port.out.SaveProduct;
import com.acme.products.project.domain.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateProductServiceMoreTest {

    @Test
    void create_assignsIdAndDefaultsPrice() {
        SaveProduct save = mock(SaveProduct.class);
        CreateProductService svc = new CreateProductService(save);

        Product in = new Product("in-id","n",BigDecimal.ZERO,"USD","ACTIVE",Instant.now(),Instant.now());
        when(save.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var out = svc.create(in);
        assertNotNull(out.getId());
        assertEquals(BigDecimal.ZERO, out.getPrice());
    }

}
