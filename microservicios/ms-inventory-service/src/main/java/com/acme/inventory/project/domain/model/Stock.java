package com.acme.inventory.project.domain.model;

import java.time.Instant;
import java.util.Objects;

public class Stock {
    private final String productId;
    private final int quantity;
    private final Instant updatedAt;

    public Stock(String productId, int quantity, Instant updatedAt) {
        if (quantity < 0)
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        this.productId = Objects.requireNonNull(productId);
        this.quantity = quantity;
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Stock withQuantity(int newQty) {
        if (newQty < 0)
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        return new Stock(productId, newQty, Instant.now());
    }
}
