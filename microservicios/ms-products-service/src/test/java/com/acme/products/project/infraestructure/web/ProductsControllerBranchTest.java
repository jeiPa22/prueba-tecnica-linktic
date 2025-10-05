package com.acme.products.project.infraestructure.web;

import com.acme.products.project.application.port.in.CreateProduct;
import com.acme.products.project.application.port.in.GetProduct;
import com.acme.products.project.application.port.in.ListProducts;
import com.acme.products.project.application.port.in.UpdateProduct;
import com.acme.products.project.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductsControllerBranchTest {

    @Test
    void create_throws_when_data_not_map() {
        var ctrl = new ProductsController(mock(CreateProduct.class), mock(GetProduct.class), mock(UpdateProduct.class), mock(ListProducts.class));
        Map<String, Object> bad = Map.of("data", "not-a-map");
        assertThrows(IllegalArgumentException.class, () -> ctrl.create(bad));
    }

    @Test
    void create_throws_when_attributes_not_map() {
        var ctrl = new ProductsController(mock(CreateProduct.class), mock(GetProduct.class), mock(UpdateProduct.class), mock(ListProducts.class));
        Map<String, Object> bad = Map.of("data", Map.of("attributes", "nope"));
        assertThrows(IllegalArgumentException.class, () -> ctrl.create(bad));
    }

    @Test
    void patch_accepts_null_attributes_and_calls_update() {
        var create = mock(CreateProduct.class);
        var get = mock(GetProduct.class);
        var update = mock(UpdateProduct.class);
        var list = mock(ListProducts.class);
        var ctrl = new ProductsController(create, get, update, list);

        var p = new Product("1", "n", BigDecimal.ONE, "USD", "ACTIVE", Instant.now(), Instant.now());
        when(update.update(anyString(), any(), any(), any(), any())).thenReturn(p);

        Map<String, Object> body = Map.of("data", Map.of());
        var resp = (org.springframework.http.ResponseEntity<com.acme.products.project.infraestructure.jsonapi.DataDocument>) ctrl.patch("1", body);
        assertEquals(200, resp.getStatusCode().value());
        verify(update).update(eq("1"), isNull(), isNull(), isNull(), isNull());
    }

    @Test
    void list_builds_links_next_prev() {
        var create = mock(CreateProduct.class);
        var get = mock(GetProduct.class);
        var update = mock(UpdateProduct.class);
        var list = mock(ListProducts.class);

        var ctrl = new ProductsController(create, get, update, list);

        var content = List.of(new Product("1", "n", BigDecimal.ONE, "USD", "ACTIVE", Instant.now(), Instant.now()));
        var page = new PageImpl<>(content, org.springframework.data.domain.PageRequest.of(1, 1), 3);
        when(list.list(any(), any(), any())).thenReturn(page);

    var resp = ctrl.list(2, 1, null, null, "localhost");
    assertEquals(200, resp.getStatusCode().value());
    var doc = resp.getBody();
        assertNotNull(doc);
        assertTrue(((Map<?, ?>)doc.links()).containsKey("self"));
        // next should exist because page has next
        assertTrue(((Map<?, ?>)doc.links()).containsKey("next"));
        // prev should exist because there is a previous page
        assertTrue(((Map<?, ?>)doc.links()).containsKey("prev"));
    }
}
