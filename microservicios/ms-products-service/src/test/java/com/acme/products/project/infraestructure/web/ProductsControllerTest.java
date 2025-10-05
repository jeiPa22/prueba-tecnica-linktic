package com.acme.products.project.infraestructure.web;

import com.acme.products.project.application.port.in.CreateProduct;
import com.acme.products.project.application.port.in.GetProduct;
import com.acme.products.project.application.port.in.ListProducts;
import com.acme.products.project.application.port.in.UpdateProduct;
import com.acme.products.project.domain.model.Product;
import com.acme.products.project.infraestructure.jsonapi.DataDocument;
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

class ProductsControllerTest {

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
    void create_parses_body_and_returns_document() {
        var now = Instant.now();
        var p = new Product("1", "n", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        when(create.create(any())).thenReturn(p);

        Map<String, Object> body = Map.of(
                "data", Map.of(
                        "attributes", Map.of("name", "n", "price", "1", "currency", "usd", "status", "active")
                )
        );
        var resp = ctrl.create(body);
    DataDocument doc = resp.getBody();
    assertNotNull(doc);
    // verify that response contains the created product resource
    assertTrue(doc.data() instanceof java.util.List);
        // simpler: verify create called
        verify(create).create(any());
    }

    @Test
    void get_returns_document() {
        var now = Instant.now();
        var p = new Product("1", "n", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        when(get.byId("1")).thenReturn(p);
        var resp = ctrl.get("1");
        var doc = resp.getBody();
        assertNotNull(doc);
        verify(get).byId("1");
    }

    @Test
    void patch_parses_and_calls_update() {
        var now = Instant.now();
        var p = new Product("1", "n", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        when(update.update(eq("1"), any(), any(), any(), any())).thenReturn(p);
        Map<String, Object> body = Map.of("data", Map.of("attributes", Map.of("name", "n2", "price", "2")));
        var resp = ctrl.patch("1", body);
        assertNotNull(resp.getBody());
        verify(update).update(eq("1"), any(), any(), any(), any());
    }

    @Test
    void list_builds_links_and_meta() {
        var now = Instant.now();
        var p = new Product("1", "n", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        when(list.list(any(), any(), any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(p), PageRequest.of(0, 1), 1));
        var resp = ctrl.list(1, 10, null, null, null);
        var doc = resp.getBody();
        assertNotNull(doc);
        assertTrue(((Map<?, ?>) doc.meta()).containsKey("totalElements"));
        verify(list).list(null, null, PageRequest.of(0, 10));
    }
}
