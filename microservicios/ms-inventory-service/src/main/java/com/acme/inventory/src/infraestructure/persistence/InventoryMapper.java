package com.acme.inventory.src.infraestructure.persistence;

import com.acme.inventory.src.domain.model.Stock;
import java.time.Instant;

public final class InventoryMapper {
    /**
     * Constructor privado para evitar instanciación
     */
    private InventoryMapper() {
    }

    /**
     * Convierte un Stock de dominio a una entidad JPA
     * 
     * @param s El stock del dominio
     * @return La entidad JPA correspondiente
     */
    public static InventoryEntity toEntity(Stock s) {
        return new InventoryEntity(s.getProductId(), s.getQuantity(), s.getUpdatedAt());
    }

    /**
     * Convierte una entidad JPA a un Stock de dominio
     * 
     * @param e La entidad JPA
     * @return El stock del dominio correspondiente
     */
    public static Stock toDomain(InventoryEntity e) {
        return new Stock(e.getProductId(), e.getQuantity(), e.getUpdatedAt());
    }

    /**
     * Actualiza una entidad JPA existente con los datos de un Stock de dominio
     * 
     * @param e La entidad JPA a actualizar
     * @param s El stock del dominio con los nuevos datos
     * @return La entidad JPA actualizada
     */
    public static InventoryEntity upsertFromDomain(InventoryEntity e, Stock s) {
        e.setQuantity(s.getQuantity());
        e.setUpdatedAt(Instant.now());
        return e;
    }
}