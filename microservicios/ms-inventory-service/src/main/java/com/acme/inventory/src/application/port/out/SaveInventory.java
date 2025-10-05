package com.acme.inventory.src.application.port.out;

import com.acme.inventory.src.domain.model.Stock;

public interface SaveInventory {
    /**
     * Guarda o actualiza el stock de un producto
     * 
     * @param stock el stock a guardar o actualizar
     * @return el stock guardado o actualizado
     */
    Stock save(Stock stock);
}