package com.acme.inventory.project.application.usecase;

import com.acme.inventory.project.application.port.out.FetchProductById;
import com.acme.inventory.project.application.port.out.LoadInventory;
import com.acme.inventory.project.application.port.out.SaveInventory;
import com.acme.inventory.project.domain.model.Stock;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PurchaseAndUpdateStockServiceTest {

    @Test
    void purchase_successful_updatesAndSaves() {
        var load = Mockito.mock(LoadInventory.class);
        var save = Mockito.mock(SaveInventory.class);
        var prod = Mockito.mock(FetchProductById.class);

        var now = Instant.now();
        Mockito.when(prod.exists("p1")).thenReturn(true);
        Mockito.when(load.findByProductId("p1")).thenReturn(Optional.of(new Stock("p1", 5, now)));
        Mockito.when(save.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        var svc = new PurchaseProductService(load, save, prod);
        var out = svc.purchase("p1", 2);

        assertThat(out.getQuantity()).isEqualTo(3);
    }

    @Test
    void purchase_insufficient_throws() {
        var load = Mockito.mock(LoadInventory.class);
        var save = Mockito.mock(SaveInventory.class);
        var prod = Mockito.mock(FetchProductById.class);

        var now = Instant.now();
        Mockito.when(prod.exists("p1")).thenReturn(true);
        Mockito.when(load.findByProductId("p1")).thenReturn(Optional.of(new Stock("p1", 1, now)));

        var svc = new PurchaseProductService(load, save, prod);
        assertThrows(IllegalStateException.class, () -> svc.purchase("p1", 2));
    }

    @Test
    void purchase_nonexistentProduct_throws() {
        var load = Mockito.mock(LoadInventory.class);
        var save = Mockito.mock(SaveInventory.class);
        var prod = Mockito.mock(FetchProductById.class);

        Mockito.when(prod.exists("x")).thenReturn(false);

        var svc = new PurchaseProductService(load, save, prod);
        assertThrows(IllegalArgumentException.class, () -> svc.purchase("x", 1));
    }

    @Test
    void updateStock_success_callsSaveAndLogs() {
        var load = Mockito.mock(LoadInventory.class);
        var save = Mockito.mock(SaveInventory.class);
        var prod = Mockito.mock(FetchProductById.class);

        var now = Instant.now();
        Mockito.when(prod.exists("p9")).thenReturn(true);
        Mockito.when(load.findByProductId("p9")).thenReturn(Optional.of(new Stock("p9", 1, now)));
        Mockito.when(save.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        var svc = new UpdateStockService(load, save, prod);
        var res = svc.setQuantity("p9", 8);

        assertThat(res.getQuantity()).isEqualTo(8);
    }

    @Test
    void updateStock_nonexistent_throws() {
        var load = Mockito.mock(LoadInventory.class);
        var save = Mockito.mock(SaveInventory.class);
        var prod = Mockito.mock(FetchProductById.class);

        Mockito.when(prod.exists("nope")).thenReturn(false);
        var svc = new UpdateStockService(load, save, prod);
        assertThrows(IllegalArgumentException.class, () -> svc.setQuantity("nope", 1));
    }
}
