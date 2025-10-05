package com.acme.products.project.application.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.acme.products.project.application.port.in.ListProducts;
import com.acme.products.project.application.port.out.SearchProducts;
import com.acme.products.project.domain.model.Product;

/**
 * Implementación del caso de uso para listar productos.
 */
@Service
public class ListProductsService implements ListProducts {
    /**
     * Puerto de salida para buscar productos.
     */
    private final SearchProducts search;

    /**
     * Constructor de la clase ListProductsService.
     * 
     * @param search Puerto de salida para buscar productos.
     */
    public ListProductsService(SearchProducts search) {
        this.search = search;
    }

    /**
     * Lista productos con paginación y filtros opcionales.
     * 
     * @param name     Filtro por nombre del producto (opcional).
     * @param status   Filtro por estado del producto (opcional).
     * @param pageable Información de paginación.
     * @return Página de productos que cumplen con los filtros.
     */
    @Override
    public Page<Product> list(String name, String status, Pageable pageable) {
        return search.search(name, status, pageable);
    }
}