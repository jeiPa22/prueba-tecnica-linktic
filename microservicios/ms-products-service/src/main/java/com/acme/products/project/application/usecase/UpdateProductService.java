package com.acme.products.project.application.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.acme.products.project.application.port.in.UpdateProduct;
import com.acme.products.project.application.port.out.LoadProduct;
import com.acme.products.project.application.port.out.SaveProduct;
import com.acme.products.project.domain.model.Product;

import java.math.BigDecimal;

/**
 * Implementación del caso de uso para actualizar un producto.
 */
@Service
public class UpdateProductService implements UpdateProduct {
    /**
     * Logger para la clase UpdateProductService.
     */
    private static final Logger log = LoggerFactory.getLogger(UpdateProductService.class);
    /**
     * Puerto de salida para cargar productos.
     */
    private final LoadProduct load;
    /**
     * Puerto de salida para guardar productos.
     */
    private final SaveProduct save;

    /**
     * Constructor de la clase UpdateProductService.
     * 
     * @param load Puerto de salida para cargar productos.
     * @param save Puerto de salida para guardar productos.
     */
    public UpdateProductService(LoadProduct load, SaveProduct save) {
        this.load = load;
        this.save = save;
    }

    /**
     * Actualiza un producto existente.
     * 
     * @param id       Identificador del producto a actualizar.
     * @param name     Nuevo nombre del producto.
     * @param price    Nuevo precio del producto.
     * @param currency Nueva moneda del producto.
     * @param status   Nuevo estado del producto.
     * @return Producto actualizado.
     * @throws IllegalArgumentException si el producto no existe.
     */
    @Override
    public Product update(String id, String name, BigDecimal price, String currency, String status) {
        var current = load.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
        var updated = current.withUpdated(name, price, currency, status);
        var saved = save.save(updated);
        log.info("product.updated id={} oldName={} newName={} oldPrice={} newPrice={} oldStatus={} newStatus={}",
                id, current.getName(), saved.getName(), current.getPrice(), saved.getPrice(), current.getStatus(),
                saved.getStatus());
        return saved;
    }
}