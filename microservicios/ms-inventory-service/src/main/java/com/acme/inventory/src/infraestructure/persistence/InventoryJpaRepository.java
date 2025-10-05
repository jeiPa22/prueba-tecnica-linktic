package com.acme.inventory.src.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryJpaRepository extends JpaRepository<InventoryEntity, String> {
}
