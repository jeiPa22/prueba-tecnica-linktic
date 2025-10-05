package com.acme.products.project.application.port.in;

import com.acme.products.project.domain.model.Product;

/**
 * Puerto de entrada para obtener un producto por su ID.
 */
public interface GetProduct {
    /**
     * Obtiene un producto por su ID.
     * 
     * @param id el ID del producto
     * @return el producto encontrado, o null si no existe
     */
    Product byId(String id);
}