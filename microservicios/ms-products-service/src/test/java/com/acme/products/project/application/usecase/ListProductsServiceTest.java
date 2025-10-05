package com.acme.products.project.application.usecase;

import com.acme.products.project.application.port.out.SearchProducts;
import com.acme.products.project.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListProductsServiceTest {

    @Test
    void list_delegatesToSearch() {
        SearchProducts sp = mock(SearchProducts.class);
        ListProductsService svc = new ListProductsService(sp);

        Product p = new Product("id","n",BigDecimal.ONE,"USD","ACTIVE",Instant.now(),Instant.now());
        Page<Product> page = new PageImpl<>(List.of(p));
        when(sp.search(null, null, Pageable.unpaged())).thenReturn(page);

        var out = svc.list(null, null, Pageable.unpaged());
        assertEquals(1, out.getTotalElements());
    }
}
