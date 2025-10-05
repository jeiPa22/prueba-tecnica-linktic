package com.acme.products.project.infraestructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.acme.products.project.application.port.out.LoadProduct;
import com.acme.products.project.application.port.out.SaveProduct;
import com.acme.products.project.application.port.out.SearchProducts;
import com.acme.products.project.domain.model.Product;

import java.util.Optional;

/**
 * Adaptador JPA para la gestión de productos, implementando los puertos de
 */
@Repository
public class ProductJpaAdapter implements LoadProduct, SaveProduct, SearchProducts {
    /**
     * Repositorio JPA para la entidad ProductEntity.
     */
    private final ProductJpaRepository repo;

    /**
     * Constructor del adaptador JPA.
     * 
     * @param repo Repositorio JPA para la entidad ProductEntity.
     */
    public ProductJpaAdapter(ProductJpaRepository repo) {
        this.repo = repo;
    }

    /**
     * Carga un producto por su ID.
     */
    @Override
    public Optional<Product> findById(String id) {
        return repo.findById(id).map(ProductMapper::toDomain);
    }

    /**
     * Guarda un producto en la base de datos.
     */
    @Override
    public Product save(Product p) {
        var saved = repo.save(ProductMapper.toEntity(p));
        return ProductMapper.toDomain(saved);
    }

    /**
     * Busca productos por nombre y estado, con paginación.
     * Si el nombre es nulo, se considera una cadena vacía.
     */
    @Override
    public Page<Product> search(String name, String status, Pageable pageable) {
        var n = (name == null) ? "" : name; // "" con Containing = sin filtro de nombre
        if (status == null || status.isBlank()) {
            return repo.findByNameContainingIgnoreCase(n, pageable)
                    .map(ProductMapper::toDomain);
        }
        var s = status.toUpperCase();
        return repo.findByNameContainingIgnoreCaseAndStatus(n, s, pageable)
                .map(ProductMapper::toDomain);
    }
}