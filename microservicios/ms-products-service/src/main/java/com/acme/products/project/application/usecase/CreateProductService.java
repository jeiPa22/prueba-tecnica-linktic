package com.acme.products.project.application.usecase;

import com.acme.products.project.application.port.in.CreateProduct;
import com.acme.products.project.application.port.out.SaveProduct;
import com.acme.products.project.domain.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Implementación del caso de uso para crear un producto.
 */
@Service
public class CreateProductService implements CreateProduct {
    /**
     * Logger para la clase CreateProductService.
     */
    private static final Logger log = LoggerFactory.getLogger(CreateProductService.class);
    /**
     * Puerto de salida para guardar productos.
     */
    private final SaveProduct save;

    /**
     * Constructor de la clase CreateProductService.
     * 
     * @param save Puerto de salida para guardar productos.
     */
    public CreateProductService(SaveProduct save) {
        this.save = save;
    }

    /**
     * Crea un nuevo producto.
     * 
     * @param product Producto a crear.
     * @return Producto creado con id y timestamps.
     */
    @Override
    public Product create(Product product) {
        // Asegurar id y timestamps si viene desde capa web sin setear
        var now = Instant.now();
        var id = product.getId() == null ? UUID.randomUUID().toString() : product.getId();
        var toCreate = new Product(
                id,
                product.getName(),
                product.getPrice() == null ? BigDecimal.ZERO : product.getPrice(),
                product.getCurrency(),
                product.getStatus(),
                now,
                now);
        var created = save.save(toCreate);
        log.info("product.created id={} name={} price={} status={}",
                created.getId(), created.getName(), created.getPrice(), created.getStatus());
        return created;
    }
}