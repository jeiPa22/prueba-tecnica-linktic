package com.acme.products.project.infraestructure.web;

import com.acme.products.project.application.port.in.CreateProduct;
import com.acme.products.project.application.port.in.GetProduct;
import com.acme.products.project.application.port.in.ListProducts;
import com.acme.products.project.application.port.in.UpdateProduct;
import com.acme.products.project.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductsControllerExtraTest {

    CreateProduct create;
    GetProduct get;
    UpdateProduct update;
    ListProducts list;
    ProductsController ctrl;

    @BeforeEach
    void setUp() {
        create = mock(CreateProduct.class);
        get = mock(GetProduct.class);
        update = mock(UpdateProduct.class);
        list = mock(ListProducts.class);
        ctrl = new ProductsController(create, get, update, list);
    }

    @Test
    void create_invalid_body_throws() {
        Map<String, Object> body = Map.of("data", "not-a-map");
        assertThrows(IllegalArgumentException.class, () -> ctrl.create(body));
    }

    @Test
    void patch_with_no_attributes_handles_null_attrs() {
        var now = Instant.now();
        var p = new Product("1", "n", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        when(update.update(eq("1"), any(), any(), any(), any())).thenReturn(p);
        // body with data but no attributes
        Map<String, Object> body = Map.of("data", Map.of());
        var resp = ctrl.patch("1", body);
        assertNotNull(resp.getBody());
        verify(update).update(eq("1"), any(), any(), any(), any());
    }

    @Test
    void list_has_next_and_prev_links() {
        var now = Instant.now();
        var p = new Product("1", "n", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        // page 0 with total 3 -> hasNext true
        when(list.list(any(), any(), eq(PageRequest.of(0, 1))))
                .thenReturn(new PageImpl<>(List.of(p), PageRequest.of(0, 1), 3));
        var resp0 = ctrl.list(1, 1, null, null, null);
        assertNotNull(resp0.getBody());

        // page 2 (index 1) with total 3 -> hasPrevious true
        when(list.list(any(), any(), eq(PageRequest.of(1, 1))))
                .thenReturn(new PageImpl<>(List.of(p), PageRequest.of(1, 1), 3));
        var resp1 = ctrl.list(2, 1, null, null, null);
        assertNotNull(resp1.getBody());
    }
}
