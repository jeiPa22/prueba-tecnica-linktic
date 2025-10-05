package com.acme.products.project.application.port.out;

import java.util.Optional;

import com.acme.products.project.domain.model.Product;

/**
 * Puerto de salida para cargar productos.
 */
public interface LoadProduct {
    /**
     * Busca un producto por su id.
     * 
     * @param id Identificador del producto.
     * @return Producto encontrado o vacío si no existe.
     */
    Optional<Product> findById(String id);
}