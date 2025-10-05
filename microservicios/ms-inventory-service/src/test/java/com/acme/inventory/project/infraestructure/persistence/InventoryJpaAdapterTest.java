package com.acme.inventory.project.infraestructure.persistence;

import com.acme.inventory.project.application.port.out.LoadInventory;
import com.acme.inventory.project.application.port.out.SaveInventory;
import com.acme.inventory.project.domain.model.Stock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InventoryJpaAdapterTest {

    @Test
    void findByProductId_returnsMappedDomain() {
        var repo = Mockito.mock(InventoryJpaRepository.class);
        var now = Instant.now();
        Mockito.when(repo.findById("p1")).thenReturn(Optional.of(new InventoryEntity("p1", 2, now)));

        var adapter = new InventoryJpaAdapter(repo);
        var opt = adapter.findByProductId("p1");
        assertThat(opt).isPresent();
        assertThat(opt.get().getQuantity()).isEqualTo(2);
    }

    @Test
    void save_createsNewEntity_whenMissing() {
        var repo = Mockito.mock(InventoryJpaRepository.class);
        Mockito.when(repo.findById("p2")).thenReturn(Optional.empty());
        Mockito.when(repo.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        var adapter = new InventoryJpaAdapter(repo);
        var out = adapter.save(new Stock("p2", 9, Instant.now()));
        assertThat(out.getQuantity()).isEqualTo(9);
    }

    @Test
    void save_upsertsExistingEntity() {
        var repo = Mockito.mock(InventoryJpaRepository.class);
        var existing = new InventoryEntity("p3", 1, Instant.now());
        Mockito.when(repo.findById("p3")).thenReturn(Optional.of(existing));
        Mockito.when(repo.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        var adapter = new InventoryJpaAdapter(repo);
        var out = adapter.save(new Stock("p3", 7, Instant.now()));
        assertThat(out.getQuantity()).isEqualTo(7);
    }
}
