package com.acme.inventory.project.infraestructure.web;

import com.acme.inventory.project.application.port.in.CheckStock;
import com.acme.inventory.project.application.port.in.PurchaseProduct;
import com.acme.inventory.project.application.port.in.UpdateStock;
import com.acme.inventory.project.domain.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;

class InventoryControllerTest {

    MockMvc mvc;
    CheckStock checkStock = mock(CheckStock.class);
    UpdateStock updateStock = mock(UpdateStock.class);
    PurchaseProduct purchaseProduct = mock(PurchaseProduct.class);

    @BeforeEach
    void setup() {
        var controller = new InventoryController(checkStock, updateStock, purchaseProduct);
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new com.acme.inventory.shared.error.GlobalExceptionHandler())
                .build();
    }

    @Test
    void get_returnsDataDocument() throws Exception {
        when(checkStock.byProductId("p1")).thenReturn(new Stock("p1", 5, Instant.now()));

        mvc.perform(get("/api/v1/inventory/p1").accept("application/vnd.api+json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value("p1"));
    }

    @Test
    void patch_updatesQuantity() throws Exception {
        when(updateStock.setQuantity("p1", 7)).thenReturn(new Stock("p1", 7, Instant.now()));

        var body = "{\"productId\":\"p1\",\"quantity\":7}";

        mvc.perform(patch("/api/v1/inventory").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.attributes.quantity").value(7));
    }

    @Test
    void purchase_returnsOk() throws Exception {
        when(purchaseProduct.purchase("p1", 2)).thenReturn(new Stock("p1", 3, Instant.now()));
        var body = "{\"productId\":\"p1\",\"units\":2}";

        mvc.perform(post("/api/v1/inventory/purchase").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.attributes.quantity").value(3));
    }

    @Test
    void purchase_nonexistent_returnsBadRequest() throws Exception {
        when(purchaseProduct.purchase("missing", 1)).thenThrow(new IllegalArgumentException("Producto no encontrado"));
        var body = "{\"productId\":\"missing\",\"units\":1}";

        mvc.perform(post("/api/v1/inventory/purchase").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].status").value("400"));
    }

    @Test
    void toDocument_internal_mapping() throws Exception {
        var ctrl = new InventoryController(checkStock, updateStock, purchaseProduct);
        var s = new Stock("rx", 11, Instant.now());
        var m = InventoryController.class.getDeclaredMethod("toDocument", com.acme.inventory.project.domain.model.Stock.class);
        m.setAccessible(true);
        var doc = m.invoke(ctrl, s);
        assertThat(doc).isNotNull();
    }
}
