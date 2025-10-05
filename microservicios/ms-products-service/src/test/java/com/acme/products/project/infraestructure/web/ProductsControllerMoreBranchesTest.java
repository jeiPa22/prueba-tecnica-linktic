package com.acme.products.project.infraestructure.web;

import com.acme.products.project.application.port.in.CreateProduct;
import com.acme.products.project.application.port.in.GetProduct;
import com.acme.products.project.application.port.in.ListProducts;
import com.acme.products.project.application.port.in.UpdateProduct;
import com.acme.products.project.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductsControllerMoreBranchesTest {

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
    void create_dataNotMap_throwsIllegalArgument() {
        Map<String,Object> body = new HashMap<>();
        body.put("data", "not-a-map");
        assertThrows(IllegalArgumentException.class, () -> controller.create(body));
    }

    @Test
    void create_attributesNotMap_throwsIllegalArgument() {
        Map<String,Object> data = new HashMap<>();
        data.put("attributes", "no-map");
        Map<String,Object> body = Map.of("data", data);
        assertThrows(IllegalArgumentException.class, () -> controller.create(body));
    }

    @Test
    void create_missingName_throwsIllegalArgument() {
        // omit name -> Product constructor will throw
        Map<String,Object> attrs = new HashMap<>();
        attrs.put("price", "1");
        attrs.put("currency", "usd");
        attrs.put("status", "active");
        Map<String,Object> body = Map.of("data", Map.of("attributes", attrs));
        assertThrows(IllegalArgumentException.class, () -> controller.create(body));
    }

    @Test
    void create_missingPrice_throwsIllegalArgument() {
        Map<String,Object> attrs = new HashMap<>();
        attrs.put("name", "n");
        attrs.put("currency", "usd");
        attrs.put("status", "active");
        Map<String,Object> body = Map.of("data", Map.of("attributes", attrs));
        assertThrows(IllegalArgumentException.class, () -> controller.create(body));
    }

    @Test
    void create_missingCurrency_throwsIllegalArgument() {
        Map<String,Object> attrs = new HashMap<>();
        attrs.put("name", "n");
        attrs.put("price", "1");
        attrs.put("status", "active");
        Map<String,Object> body = Map.of("data", Map.of("attributes", attrs));
        assertThrows(IllegalArgumentException.class, () -> controller.create(body));
    }

    @Test
    void create_missingStatus_throwsIllegalArgument() {
        Map<String,Object> attrs = new HashMap<>();
        attrs.put("name", "n");
        attrs.put("price", "1");
        attrs.put("currency", "usd");
        Map<String,Object> body = Map.of("data", Map.of("attributes", attrs));
        assertThrows(IllegalArgumentException.class, () -> controller.create(body));
    }

    @Test
    void patch_dataNotMap_throws() {
        assertThrows(IllegalArgumentException.class, () -> controller.patch("x", Map.of()));
    }

    @Test
    void patch_attrsNotMap_throws() {
        Map<String,Object> data = Map.of("attributes", "bad");
        Map<String,Object> body = Map.of("data", data);
        assertThrows(IllegalArgumentException.class, () -> controller.patch("x", body));
    }

    @Test
    void patch_withPrice_parsing_callsUpdateWithPrice() {
        String id = "p1";
        Map<String,Object> attrs = new HashMap<>();
        attrs.put("price", "12.34");
        Map<String,Object> data = Map.of("attributes", attrs);
        Map<String,Object> body = Map.of("data", data);

        Product p = new Product(id, "n", BigDecimal.valueOf(123), "USD", "ACTIVE", Instant.now(), Instant.now());
        when(update.update(eq(id), any(), any(), any(), any())).thenReturn(p);

        var resp = controller.patch(id, body);
        assertEquals(200, resp.getStatusCode().value());
        verify(update).update(eq(id), any(), eq(new BigDecimal("12.34")), any(), any());
    }

    @Test
    void list_withNextOnly_andPrevOnly_and_filters() {
        Product p = new Product("1","a",BigDecimal.ONE,"USD","ACTIVE",Instant.now(),Instant.now());
        var pageNextOnly = new PageImpl<>(List.of(p)){
            @Override public boolean hasNext(){return true;} @Override public boolean hasPrevious(){return false;} @Override public long getTotalElements(){return 1;} @Override public int getTotalPages(){return 1;}
        };
        var pagePrevOnly = new PageImpl<>(List.of(p)){
            @Override public boolean hasNext(){return false;} @Override public boolean hasPrevious(){return true;} @Override public long getTotalElements(){return 1;} @Override public int getTotalPages(){return 1;}
        };

        when(list.list(eq("n"), eq("active"), any(Pageable.class))).thenReturn(pageNextOnly);
        var r1 = controller.list(1,10,"n","active","host");
        assertEquals(200, r1.getStatusCode().value());
    assertTrue(r1.getBody().links().get("self").toString().contains("filter[name]=n"));

        when(list.list(isNull(), isNull(), any(Pageable.class))).thenReturn(pagePrevOnly);
        var r2 = controller.list(1,10,null,null,"host");
        assertEquals(200, r2.getStatusCode().value());
    }

    @Test
    void list_neither_next_nor_prev() {
        Product p = new Product("1","a",BigDecimal.ONE,"USD","ACTIVE",Instant.now(),Instant.now());
        var page = new PageImpl<>(List.of(p)){
            @Override public boolean hasNext(){return false;} @Override public boolean hasPrevious(){return false;} @Override public long getTotalElements(){return 1;} @Override public int getTotalPages(){return 1;}
        };
        when(list.list(isNull(), isNull(), any(Pageable.class))).thenReturn(page);
        var r = controller.list(1,10,null,null,null);
        assertEquals(200, r.getStatusCode().value());
        assertFalse(r.getBody().links().containsKey("next"));
        assertFalse(r.getBody().links().containsKey("prev"));
    }

    @Test
    void list_both_next_and_prev() {
        Product p = new Product("1","a",BigDecimal.ONE,"USD","ACTIVE",Instant.now(),Instant.now());
        var page = new PageImpl<>(List.of(p)){
            @Override public boolean hasNext(){return true;} @Override public boolean hasPrevious(){return true;} @Override public long getTotalElements(){return 5;} @Override public int getTotalPages(){return 3;}
        };
        when(list.list(isNull(), isNull(), any(Pageable.class))).thenReturn(page);
        var r = controller.list(2,10,null,null,null);
        assertEquals(200, r.getStatusCode().value());
        assertTrue(r.getBody().links().containsKey("next"));
        assertTrue(r.getBody().links().containsKey("prev"));
    }
}
