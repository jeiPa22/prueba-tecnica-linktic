package com.acme.inventory.project.application.port.out;

import com.acme.inventory.project.domain.model.Stock;

public interface SaveInventory {
    /**
     * Guarda o actualiza el stock de un producto
     * 
     * @param stock el stock a guardar o actualizar
     * @return el stock guardado o actualizado
     */
    Stock save(Stock stock);
}