package com.acme.inventory.src.application.usecase;

import org.springframework.stereotype.Service;

import com.acme.inventory.src.application.port.in.UpdateStock;
import com.acme.inventory.src.application.port.out.FetchProductById;
import com.acme.inventory.src.application.port.out.LoadInventory;
import com.acme.inventory.src.application.port.out.SaveInventory;
import com.acme.inventory.src.domain.model.Stock;

@Service
public class UpdateStockService implements UpdateStock {
    /**
     * Repositorio para cargar inventarios
     */
    private final LoadInventory load;
    /**
     * Repositorio para guardar inventarios
     */
    private final SaveInventory save;
    /**
     * Servicio para verificar la existencia de productos
     */
    private final FetchProductById products;

    /**
     * Constructor de la clase
     * 
     * @param load     el repositorio para cargar inventarios
     * @param save     el repositorio para guardar inventarios
     * @param products el servicio para verificar la existencia de productos
     */
    public UpdateStockService(LoadInventory load, SaveInventory save, FetchProductById products) {
        this.load = load;
        this.save = save;
        this.products = products;
    }

    /**
     * Actualiza la cantidad de stock de un producto
     */
    @Override
    public Stock setQuantity(String productId, int quantity) {
        if (!products.exists(productId)) {
            throw new IllegalArgumentException("Producto no encontrado: " + productId);
        }
        var current = load.findByProductId(productId)
                .orElseGet(() -> new Stock(productId, 0, java.time.Instant.now()));
        var updated = current.withQuantity(quantity);
        return save.save(updated);
    }
}