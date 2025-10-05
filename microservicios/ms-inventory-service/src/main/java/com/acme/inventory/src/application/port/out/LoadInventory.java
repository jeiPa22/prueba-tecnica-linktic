package com.acme.inventory.src.application.port.out;

import com.acme.inventory.src.domain.model.Stock;
import java.util.Optional;

public interface LoadInventory {
    /**
     * Busca el stock de un producto por su ID
     * 
     * @param productId el ID del producto
     * @return el stock del producto, si existe
     */
    Optional<Stock> findByProductId(String productId);
}