package com.acme.products.project.infraestructure.persistence;

import com.acme.products.project.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductJpaAdapterTest {

    ProductJpaRepository repo;
    ProductJpaAdapter adapter;

    @BeforeEach
    void setup() {
        repo = mock(ProductJpaRepository.class);
        adapter = new ProductJpaAdapter(repo);
    }

    @Test
    void findById_maps_to_domain() {
        var now = Instant.now();
        var ent = new ProductEntity("1", "n", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        when(repo.findById("1")).thenReturn(Optional.of(ent));
        var res = adapter.findById("1").orElseThrow();
        assertEquals("1", res.getId());
        verify(repo).findById("1");
    }

    @Test
    void save_maps_and_returns_domain() {
        var now = Instant.now();
        var domain = new Product("1", "n", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        var ent = new ProductEntity("1", "n", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        when(repo.save(ArgumentMatchers.any())).thenReturn(ent);
        var saved = adapter.save(domain);
        assertEquals(domain.getId(), saved.getId());
        verify(repo).save(ArgumentMatchers.any());
    }

    @Test
    void search_without_status_calls_findByName() {
        var now = Instant.now();
        var ent = new ProductEntity("1", "n", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        Page<ProductEntity> page = new PageImpl<>(List.of(ent));
        when(repo.findByNameContainingIgnoreCase(anyString(), any(PageRequest.class))).thenReturn(page);
        var res = adapter.search(null, null, PageRequest.of(0, 10));
        assertEquals(1, res.getTotalElements());
        verify(repo).findByNameContainingIgnoreCase("", PageRequest.of(0, 10));
    }

    @Test
    void search_with_status_calls_findByNameAndStatus() {
        var now = Instant.now();
        var ent = new ProductEntity("1", "n", BigDecimal.ONE, "USD", "ACTIVE", now, now);
        Page<ProductEntity> page = new PageImpl<>(List.of(ent));
        when(repo.findByNameContainingIgnoreCaseAndStatus(anyString(), anyString(), any(PageRequest.class))).thenReturn(page);
        var res = adapter.search("foo", "active", PageRequest.of(0, 10));
        assertEquals(1, res.getTotalElements());
        verify(repo).findByNameContainingIgnoreCaseAndStatus("foo", "ACTIVE", PageRequest.of(0, 10));
    }
}
