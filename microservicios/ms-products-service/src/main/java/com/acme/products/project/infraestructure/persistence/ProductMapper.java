package com.acme.products.project.infraestructure.persistence;

import com.acme.products.project.domain.model.Product;

/**
 * Mapper para convertir entre la entidad JPA ProductEntity y el modelo de
 * dominio Product.
 */
public final class ProductMapper {
    /**
     * Constructor privado para evitar instanciación.
     */
    private ProductMapper() {
    }

    /**
     * Convierte un objeto Product del dominio a una entidad JPA ProductEntity.
     * 
     * @param p Objeto del dominio Product.
     * @return Entidad JPA ProductEntity.
     */
    public static ProductEntity toEntity(Product p) {
        return new ProductEntity(
                p.getId(), p.getName(), p.getPrice(), p.getCurrency(), p.getStatus(), p.getCreatedAt(),
                p.getUpdatedAt());
    }

    /**
     * Convierte una entidad JPA ProductEntity a un objeto del dominio Product.
     * 
     * @param e Entidad JPA ProductEntity.
     * @return Objeto del dominio Product.
     */
    public static Product toDomain(ProductEntity e) {
        return new Product(
                e.getId(), e.getName(), e.getPrice(), e.getCurrency(), e.getStatus(), e.getCreatedAt(),
                e.getUpdatedAt());
    }
}