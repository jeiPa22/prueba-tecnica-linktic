package com.acme.inventory.src.application.usecase;

import org.springframework.stereotype.Service;

import com.acme.inventory.src.application.port.in.PurchaseProduct;
import com.acme.inventory.src.application.port.out.FetchProductById;
import com.acme.inventory.src.application.port.out.LoadInventory;
import com.acme.inventory.src.application.port.out.SaveInventory;
import com.acme.inventory.src.domain.model.Stock;

@Service
public class PurchaseProductService implements PurchaseProduct {
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
    public PurchaseProductService(LoadInventory load, SaveInventory save, FetchProductById products) {
        this.load = load;
        this.save = save;
        this.products = products;
    }

    /**
     * Compra unidades de un producto, reduciendo su stock
     */
    @Override
    public Stock purchase(String productId, int units) {
        if (units <= 0)
            throw new IllegalArgumentException("Las unidades deben ser positivas");
        if (!products.exists(productId))
            throw new IllegalArgumentException("Producto no encontrado: " + productId);
        var current = load.findByProductId(productId)
                .orElseGet(() -> new Stock(productId, 0, java.time.Instant.now()));
        if (current.getQuantity() < units)
            throw new IllegalStateException("Stock insuficiente");
        var updated = current.withQuantity(current.getQuantity() - units);
        return save.save(updated);
    }
}
