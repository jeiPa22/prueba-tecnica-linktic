package com.acme.products.project.application.port.in;

import com.acme.products.project.domain.model.Product;

/**
 * Puerto de entrada para la creación de un producto.
 */
public interface CreateProduct {
    /**
     * Crea un nuevo producto en el sistema.
     * 
     * @param product el producto a crear
     * @return el producto creado
     */
    Product create(Product product);
}
