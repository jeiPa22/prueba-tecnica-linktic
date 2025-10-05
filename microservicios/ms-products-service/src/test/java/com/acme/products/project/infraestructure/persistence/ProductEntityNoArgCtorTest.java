package com.acme.products.project.infraestructure.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityNoArgCtorTest {

    @Test
    void defaultConstructor_initializes() {
        ProductEntity e = new ProductEntity();
        assertNotNull(e);
        // ensure getters don't throw
        assertNull(e.getId());
        assertNull(e.getName());
        assertNull(e.getPrice());
    }
}
