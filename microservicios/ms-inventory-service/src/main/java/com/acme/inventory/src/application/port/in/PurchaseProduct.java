package com.acme.inventory.src.application.port.in;

import com.acme.inventory.src.domain.model.Stock;

public interface PurchaseProduct {
    /**
     * Realiza la compra de un producto, reduciendo su stock
     * 
     * @param productId ID del producto a comprar
     * @param units     Número de unidades a comprar
     * @return Stock actualizado del producto después de la compra
     */
    Stock purchase(String productId, int units);
}