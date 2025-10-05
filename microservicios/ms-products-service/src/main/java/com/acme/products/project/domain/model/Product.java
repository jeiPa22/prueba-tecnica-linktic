package com.acme.products.project.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Representa un producto en el sistema.
 */
public class Product {
    /**
     * Identificador único del producto.
     */
    private final String id;
    /**
     * Nombre del producto.
     */
    private final String name;
    /**
     * Precio del producto.
     */
    private final BigDecimal price;
    /**
     * Moneda del precio, en formato ISO-4217 (3 caracteres).
     */
    private final String currency;
    /**
     * Estado del producto.
     */
    private final String status;
    /**
     * Fecha de creación del producto.
     */
    private final Instant createdAt;
    /**
     * Fecha de actualización del producto.
     */
    private final Instant updatedAt;

    /**
     * Constructor del producto.
     * 
     * @param id        Identificador único del producto.
     * @param name      Nombre del producto.
     * @param price     Precio del producto.
     * @param currency  Moneda del precio, en formato ISO-4217 (3 caracteres).
     * @param status    Estado del producto.
     * @param createdAt Fecha de creación del producto.
     * @param updatedAt Fecha de actualización del producto.
     */
    public Product(String id, String name, BigDecimal price, String currency, String status, Instant createdAt,
            Instant updatedAt) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name is required");
        if (price == null || price.signum() < 0)
            throw new IllegalArgumentException("price must be >= 0");
        if (currency == null || currency.length() != 3)
            throw new IllegalArgumentException("currency must be ISO-4217 (3 chars)");
        if (status == null || status.isBlank())
            throw new IllegalArgumentException("status is required");
        this.id = Objects.requireNonNull(id, "id");
        this.name = name;
        this.price = price;
        this.currency = currency.toUpperCase();
        this.status = status.toUpperCase();
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt");
    }

    /**
     * getter y setter
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

    /**
     * Crea una copia del producto con los campos actualizados.
     * 
     * @param name     Nuevo nombre del producto (o null para no cambiarlo).
     * @param price    Nuevo precio del producto (o null para no cambiarlo).
     * @param currency Nueva moneda del precio (o null para no cambiarlo).
     * @param status   Nuevo estado del producto (o null para no cambiarlo).
     * @return una copia del producto con los campos actualizados y la fecha de
     *         actualización modificada.
     */
    public Product withUpdated(String name, BigDecimal price, String currency, String status) {
        return new Product(
                this.id,
                name == null ? this.name : name,
                price == null ? this.price : price,
                currency == null ? this.currency : currency.toUpperCase(),
                status == null ? this.status : status.toUpperCase(),
                this.createdAt,
                Instant.now());
    }
}
