package com.acme.products.project.application.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.acme.products.project.domain.model.Product;

/**
 * Puerto de entrada para listar productos con paginación y filtros opcionales.
 */
public interface ListProducts {
    /**
     * Lista productos con paginación y filtros opcionales.
     * 
     * @param name     el nombre del producto (opcional)
     * @param status   el estado del producto (opcional)
     * @param pageable la información de paginación
     * @return una página de productos
     */
    Page<Product> list(String name, String status, Pageable pageable);
}
