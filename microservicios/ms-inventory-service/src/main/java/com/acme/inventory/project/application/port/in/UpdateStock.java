package com.acme.inventory.project.application.port.in;

import com.acme.inventory.project.domain.model.Stock;

public interface UpdateStock {
    /**
     * Actualiza la cantidad de stock de un producto
     * 
     * @param productId ID del producto
     * @param quantity  Nueva cantidad de stock
     * @return Stock actualizado del producto
     */
    Stock setQuantity(String productId, int quantity);
}