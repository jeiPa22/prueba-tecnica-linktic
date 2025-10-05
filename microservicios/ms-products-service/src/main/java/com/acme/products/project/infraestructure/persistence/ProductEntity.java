package com.acme.products.project.infraestructure.persistence;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Entidad JPA que representa un producto en la base de datos.
 */
@Entity
@Table(name = "products")
public class ProductEntity {

    /**
     * Identificador único del producto.
     */
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    /**
     * Nombre del producto.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Precio del producto.
     */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /**
     * Moneda del precio del producto (ISO 4217).
     */
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    /**
     * Estado del producto (e.g., "ACTIVE", "INACTIVE").
     */
    @Column(name = "status", nullable = false)
    private String status;

    /**
     * Fecha y hora de creación del producto.
     */
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /**
     * Fecha y hora de actualización del producto.
     */
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Constructor por defecto requerido por JPA.
     */
    protected ProductEntity() {
    }

    /**
     * Constructor completo de la entidad ProductEntity.
     * 
     * @param id        Identificador único del producto.
     * @param name      Nombre del producto.
     * @param price     Precio del producto.
     * @param currency  Moneda del precio del producto.
     * @param status    Estado del producto.
     * @param createdAt Fecha y hora de creación del producto.
     * @param updatedAt Fecha y hora de actualización del producto.
     */
    public ProductEntity(String id, String name, BigDecimal price, String currency, String status, Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /*
     * Getters y Setters
     */
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}