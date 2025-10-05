package com.acme.products.project.application.port.out;

import com.acme.products.project.domain.model.Product;

/**
 * Puerto de salida para guardar un producto.
 */
public interface SaveProduct {
    /**
     * Guarda un producto.
     * 
     * @param product el producto a guardar
     * @return el producto guardado
     */
    Product save(Product product);
}