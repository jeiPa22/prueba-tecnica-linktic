package com.acme.inventory.src.infraestructure.persistence;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "inventory")
public class InventoryEntity {

    /**
     * El ID del producto al que pertenece este stock
     */
    @Id
    @Column(name = "product_id", nullable = false, updatable = false)
    private String productId;

    /**
     * La cantidad disponible en stock
     */
    @Column(name = "quantity", nullable = false)
    private int quantity;

    /**
     * La fecha y hora de la última actualización del stock
     */
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Constructor por defecto requerido por JPA
     */
    protected InventoryEntity() {
    }

    /**
     * Constructor completo
     *
     * @param productId El ID del producto
     * @param quantity  La cantidad en stock
     * @param updatedAt La fecha y hora de la última actualización
     */
    public InventoryEntity(String productId, int quantity, Instant updatedAt) {
        this.productId = productId;
        this.quantity = quantity;
        this.updatedAt = updatedAt;
    }

    /**
     * Getters y setters
     */
    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * equals y hashCode basados en productId (clave primaria)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof InventoryEntity that))
            return false;
        return Objects.equals(productId, that.productId);
    }

    /**
     * hashCode basado en productId (clave primaria)
     */
    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
