package com.acme.products.project.infraestructure.web;

import com.acme.products.project.application.port.in.CreateProduct;
import com.acme.products.project.application.port.in.GetProduct;
import com.acme.products.project.application.port.in.ListProducts;
import com.acme.products.project.application.port.in.UpdateProduct;
import com.acme.products.project.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductsControllerFullBranchesTest {

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
    void create_withAllAttributes_returns200() {
        String id = UUID.randomUUID().toString();
        Product in = new Product(id, "n", BigDecimal.TEN, "USD", "ACTIVE", Instant.now(), Instant.now());
        when(create.create(any())).thenReturn(in);

        Map<String,Object> attrs = Map.of("name","n","price",10,"currency","usd","status","active");
        Map<String,Object> data = Map.of("attributes", attrs);
        Map<String,Object> body = Map.of("data", data);

        var resp = controller.create(body);
        // resp is ResponseEntity<DataDocument>
        assertEquals(200, ((org.springframework.http.ResponseEntity<?>)resp).getStatusCode().value());
        assertNotNull(((org.springframework.http.ResponseEntity<?>)resp).getBody());
    }

    @Test
    void get_returns200() {
        String id = "p";
        Product p = new Product(id, "n", BigDecimal.ONE, "EUR", "ACTIVE", Instant.now(), Instant.now());
        when(get.byId(eq(id))).thenReturn(p);
        var resp = controller.get(id);
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void list_withNextPrev_buildsLinks() {
        Product p1 = new Product("1","a",BigDecimal.ONE,"USD","ACTIVE",Instant.now(),Instant.now());
        Product p2 = new Product("2","b",BigDecimal.ONE,"USD","ACTIVE",Instant.now(),Instant.now());
        var page = new PageImpl<>(List.of(p1,p2)){
            @Override public boolean hasNext(){return true;}
            @Override public boolean hasPrevious(){return true;}
            @Override public long getTotalElements(){return 20;}
            @Override public int getTotalPages(){return 10;}
        };
        when(list.list(isNull(), isNull(), any())).thenReturn(page);

        var resp = controller.list(2,2,null,null,"localhost");
        assertEquals(200, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
    }
}
