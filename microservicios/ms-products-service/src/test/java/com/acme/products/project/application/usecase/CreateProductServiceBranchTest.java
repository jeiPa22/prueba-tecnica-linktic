package com.acme.products.project.application.usecase;

import com.acme.products.project.application.port.out.SaveProduct;
import com.acme.products.project.domain.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateProductServiceBranchTest {

    @Test
    void create_sets_zero_price_when_null_and_keeps_id_if_present() {
        var save = mock(SaveProduct.class);
        var svc = new CreateProductService(save);

    var in = mock(Product.class);
    when(in.getId()).thenReturn("given-id");
    when(in.getName()).thenReturn("name");
    when(in.getPrice()).thenReturn(null);
    when(in.getCurrency()).thenReturn("USD");
    when(in.getStatus()).thenReturn("ACTIVE");
    when(save.save(any())).thenAnswer(i -> i.getArgument(0));

    var out = svc.create(in);
        assertNotNull(out.getId());
        assertEquals("given-id", out.getId());
        assertEquals(BigDecimal.ZERO, out.getPrice());
        verify(save).save(any());
    }

    @Test
    void create_generates_id_when_input_has_null_id() {
        var save = mock(SaveProduct.class);
        var svc = new CreateProductService(save);

        var in = mock(Product.class);
        when(in.getId()).thenReturn(null);
        when(in.getName()).thenReturn("name");
        when(in.getPrice()).thenReturn(BigDecimal.ONE);
        when(in.getCurrency()).thenReturn("USD");
        when(in.getStatus()).thenReturn("ACTIVE");
        when(save.save(any())).thenAnswer(i -> i.getArgument(0));

        var out = svc.create(in);
        assertNotNull(out.getId());
        verify(save).save(any());
    }
}
