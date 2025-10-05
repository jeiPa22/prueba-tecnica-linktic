package com.acme.inventory.project.application.usecase;

import com.acme.inventory.project.application.port.out.LoadInventory;
import com.acme.inventory.project.domain.model.Stock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CheckStockServiceTest {

    @Test
    void byProductId_returnsExisting() {
        var load = Mockito.mock(LoadInventory.class);
        var now = Instant.now();
        Mockito.when(load.findByProductId("p1")).thenReturn(Optional.of(new Stock("p1", 7, now)));

        var svc = new CheckStockService(load);
        var res = svc.byProductId("p1");

        assertThat(res.getProductId()).isEqualTo("p1");
        assertThat(res.getQuantity()).isEqualTo(7);
    }

    @Test
    void byProductId_returnsZeroWhenMissing() {
        var load = Mockito.mock(LoadInventory.class);
        Mockito.when(load.findByProductId("p2")).thenReturn(Optional.empty());

        var svc = new CheckStockService(load);
        var res = svc.byProductId("p2");

        assertThat(res.getProductId()).isEqualTo("p2");
        assertThat(res.getQuantity()).isEqualTo(0);
    }
}
