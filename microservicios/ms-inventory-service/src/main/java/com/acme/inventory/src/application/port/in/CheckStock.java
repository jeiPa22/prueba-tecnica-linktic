package com.acme.inventory.src.application.port.in;

import com.acme.inventory.src.domain.model.Stock;

public interface CheckStock {
    /**
     * Consulta el stock disponible de un producto por su ID
     * 
     * @param productId ID del producto
     * @return Stock disponible del producto
     */
    Stock byProductId(String productId);
}