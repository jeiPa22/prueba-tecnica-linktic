package com.acme.products.project.application.usecase;

import org.springframework.stereotype.Service;

import com.acme.products.project.application.port.in.GetProduct;
import com.acme.products.project.application.port.out.LoadProduct;
import com.acme.products.project.domain.model.Product;

/**
 * Implementación del caso de uso para obtener un producto por su id.
 */
@Service
public class GetProductService implements GetProduct {
    /**
     * Puerto de salida para cargar productos.
     */
    private final LoadProduct load;

    /**
     * Constructor de la clase GetProductService.
     * 
     * @param load Puerto de salida para cargar productos.
     */
    public GetProductService(LoadProduct load) {
        this.load = load;
    }

    /**
     * Busca un producto por su id.
     * 
     * @param id Identificador del producto.
     * @return Producto encontrado.
     * @throws IllegalArgumentException si el producto no existe.
     */
    @Override
    public Product byId(String id) {
        return load.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
    }
}
