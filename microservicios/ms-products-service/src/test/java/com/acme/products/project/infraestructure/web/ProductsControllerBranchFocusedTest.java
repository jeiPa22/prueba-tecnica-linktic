package com.acme.products.project.infraestructure.web;

import com.acme.products.project.application.port.in.CreateProduct;
import com.acme.products.project.application.port.in.GetProduct;
import com.acme.products.project.application.port.in.ListProducts;
import com.acme.products.project.application.port.in.UpdateProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.acme.products.project.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class ProductsControllerBranchFocusedTest {

    CreateProduct create = mock(CreateProduct.class);
    GetProduct get = mock(GetProduct.class);
    UpdateProduct update = mock(UpdateProduct.class);
    ListProducts list = mock(ListProducts.class);

    ProductsController controller;

    @BeforeEach
    void setUp(){
        controller = new ProductsController(create, get, update, list);
    }

    @Test
    void create_missingAttributes_throwsIllegalArgument() {
        Map<String,Object> body = new HashMap<>();
        // missing data -> controller should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> controller.create(body));
    }

    @Test
    void patch_withEmptyAttributes_returnsOk_whenUpdateReturnsProduct() {
        String id = "p1";
        Map<String,Object> body = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        data.put("id", id);
        data.put("type", "products");
        data.put("attributes", new HashMap<>());
        body.put("data", data);
        // mock update to return a valid product so controller can convert to document
        Product p = new Product(id, "name", BigDecimal.ZERO, "USD", "ACTIVE", Instant.now(), Instant.now());
    when(update.update(eq(id), isNull(), isNull(), isNull(), isNull())).thenReturn(p);

        var resp = controller.patch(id, body);
        assertNotNull(resp);
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void list_paginationEdges_returnsOkWithEmpty() {
        // mock list to return an empty page
    when(list.list(isNull(), isNull(), any(Pageable.class))).thenReturn(Page.empty());
        var resp = controller.list(0,0,null,null,null);
        assertNotNull(resp);
        // controller returns 200 even when empty page
        assertEquals(200, resp.getStatusCode().value());
    }
}
