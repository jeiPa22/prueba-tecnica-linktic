package com.acme.products.project.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Repositorio JPA para la entidad ProductEntity.
 */
public interface ProductJpaRepository extends JpaRepository<ProductEntity, String> {
    /**
     * Busca productos por nombre (contiene, sin distinguir mayúsculas) y estado,
     * con paginación.
     *
     * @param name     Nombre o parte del nombre del producto a buscar.
     * @param status   Estado del producto a filtrar (e.g., "ACTIVE").
     * @param pageable Información de paginación.
     * @return Página de productos que coinciden con los criterios de búsqueda.
     */
    Page<ProductEntity> findByNameContainingIgnoreCaseAndStatus(String name, String status, Pageable pageable);
}