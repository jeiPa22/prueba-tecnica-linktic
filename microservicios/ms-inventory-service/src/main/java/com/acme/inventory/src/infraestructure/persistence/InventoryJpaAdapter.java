package com.acme.inventory.src.infraestructure.persistence;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.acme.inventory.src.application.port.out.LoadInventory;
import com.acme.inventory.src.application.port.out.SaveInventory;
import com.acme.inventory.src.domain.model.Stock;

@Repository
public class InventoryJpaAdapter implements LoadInventory, SaveInventory {

    /**
     * Repositorio JPA para la entidad Inventory
     */
    private final InventoryJpaRepository repo;

    /**
     * Constructor de la clase
     * 
     * @param repo el repositorio JPA inyectado
     */
    public InventoryJpaAdapter(InventoryJpaRepository repo) {
        this.repo = repo;
    }

    /**
     * Busca el stock de un producto por su ID
     */
    @Override
    public Optional<Stock> findByProductId(String productId) {
        return repo.findById(productId).map(InventoryMapper::toDomain);
    }

    /**
     * Guarda o actualiza el stock de un producto
     */
    @Override
    public Stock save(Stock stock) {
        var entity = repo.findById(stock.getProductId())
                .map(e -> InventoryMapper.upsertFromDomain(e, stock))
                .orElseGet(() -> InventoryMapper.toEntity(new Stock(
                        stock.getProductId(), stock.getQuantity(), Instant.now())));
        return InventoryMapper.toDomain(repo.save(entity));
    }
}