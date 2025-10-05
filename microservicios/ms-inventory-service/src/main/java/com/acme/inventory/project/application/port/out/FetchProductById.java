package com.acme.inventory.project.application.port.out;

public interface FetchProductById {
    /**
     * Verifica si un producto existe por su ID
     * 
     * @param productId ID del producto a verificar
     * @return true si el producto existe, false en caso contrario
     */
    boolean exists(String productId);
}