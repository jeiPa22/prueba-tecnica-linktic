package com.acme.products.project.application.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.acme.products.project.domain.model.Product;

/**
 * Puerto de salida para buscar productos con paginación y filtros opcionales.
 */
public interface SearchProducts {
    /**
     * Busca productos con paginación y filtros opcionales.
     * 
     * @param name     nombre del producto (opcional)
     * @param status   estao del producto (opcional)
     * @param pageable información de paginación
     * @return una página de productos
     */
    Page<Product> search(String name, String status, Pageable pageable);
}