package com.acme.inventory.project.application.port.out;

import java.util.Optional;

import com.acme.inventory.project.domain.model.Stock;

public interface LoadInventory {
    /**
     * Busca el stock de un producto por su ID
     * 
     * @param productId el ID del producto
     * @return el stock del producto, si existe
     */
    Optional<Stock> findByProductId(String productId);
}