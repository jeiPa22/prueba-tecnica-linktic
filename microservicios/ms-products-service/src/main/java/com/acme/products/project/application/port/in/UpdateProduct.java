package com.acme.products.project.application.port.in;

import com.acme.products.project.domain.model.Product;

/**
 * Puerto de entrada para actualizar un producto.
 */
public interface UpdateProduct {
    /**
     * Actualiza un producto.
     * 
     * @param id       el ID del producto
     * @param name     el nuevo nombre del producto
     * @param price    el nuevo precio del producto
     * @param currency la nueva moneda del producto
     * @param status   el nuevo estado del producto
     * @return el producto actualizado
     */
    Product update(String id, String name, java.math.BigDecimal price, String currency, String status);
}