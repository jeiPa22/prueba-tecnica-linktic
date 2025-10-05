package com.acme.inventory.project.application.usecase;

import org.springframework.stereotype.Service;

import com.acme.inventory.project.application.port.in.CheckStock;
import com.acme.inventory.project.application.port.out.LoadInventory;
import com.acme.inventory.project.domain.model.Stock;

@Service
public class CheckStockService implements CheckStock {
    /**
     * Repositorio para cargar inventarios
     */
    private final LoadInventory loadInventory;

    /**
     * Constructor de la clase
     * 
     * @param loadInventory el repositorio para cargar inventarios
     */
    public CheckStockService(LoadInventory loadInventory) {
        this.loadInventory = loadInventory;
    }

    /**
     * Consulta el stock de un producto por su ID
     */
    @Override
    public Stock byProductId(String productId) {
        return loadInventory.findByProductId(productId)
                .orElseGet(() -> new Stock(productId, 0, java.time.Instant.now()));
    }
}