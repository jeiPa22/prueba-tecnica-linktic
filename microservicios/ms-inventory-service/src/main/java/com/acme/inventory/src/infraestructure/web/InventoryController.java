package com.acme.inventory.src.infraestructure.web;

import java.util.Map;

import com.acme.inventory.src.application.port.in.CheckStock;
import com.acme.inventory.src.application.port.in.PurchaseProduct;
import com.acme.inventory.src.application.port.in.UpdateStock;
import com.acme.inventory.src.domain.model.Stock;
import com.acme.inventory.src.infraestructure.jsonapi.DataDocument;
import com.acme.inventory.src.infraestructure.jsonapi.ResourceObject;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador del inventario
 */
@RestController
@RequestMapping(value = "/api/inventory", produces = "application/vnd.api+json", consumes = MediaType.APPLICATION_JSON_VALUE)
public class InventoryController {
    /**
     * el caso de uso para consultar el stock
     */
    private final CheckStock checkStock;
    /**
     * el caso de uso para actualizar el stock
     */
    private final UpdateStock updateStock;
    /**
     * el caso de uso para comprar un producto
     */
    private final PurchaseProduct purchaseProduct;

    /**
     * constructor
     *
     * @param checkStock      el caso de uso para consultar el stock
     * @param updateStock     el caso de uso para actualizar el stock
     * @param purchaseProduct el caso de uso para comprar un producto
     */
    public InventoryController(CheckStock checkStock, UpdateStock updateStock, PurchaseProduct purchaseProduct) {
        this.checkStock = checkStock;
        this.updateStock = updateStock;
        this.purchaseProduct = purchaseProduct;
    }

    /**
     * request para actualizar el stock
     */
    @GetMapping(value = "/{productId}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<DataDocument> get(@PathVariable String productId) {
        Stock s = checkStock.byProductId(productId);
        return ResponseEntity.ok(toDocument(s));
    }

    /**
     * request para actualizar el stock
     * 
     * @param req la request
     * @return el stock actualizado
     */
    @PatchMapping
    public ResponseEntity<DataDocument> patch(@RequestBody UpdateStockRequest req) {
        var s = updateStock.setQuantity(req.productId(), req.quantity());
        return ResponseEntity.ok(toDocument(s));
    }

    /**
     * request para comprar un producto
     * 
     * @param req la request
     * @return el producto comprado
     */
    @PostMapping("/purchase")
    public ResponseEntity<DataDocument> purchase(@RequestBody PurchaseRequest req) {
        var s = purchaseProduct.purchase(req.productId(), req.units());
        return ResponseEntity.ok(toDocument(s));
    }

    /**
     * convierte un stock en un documento jsonapi
     * 
     * @param s el stock
     * @return el documento jsonapi
     */
    private DataDocument toDocument(Stock s) {
        Map<String, String> links = Map.of("self", "/api/inventory/" + s.getProductId());

        Map<String, Object> attrs = new java.util.LinkedHashMap<>();
        attrs.put("quantity", s.getQuantity());
        attrs.put("updatedAt", s.getUpdatedAt().toString());

        Map<String, String> relLinks = Map.of("related", "/api/products/" + s.getProductId());
        Map<String, String> relData = Map.of("type", "products", "id", s.getProductId());

        Map<String, Object> productRel = new java.util.LinkedHashMap<>();
        productRel.put("links", relLinks);
        productRel.put("data", relData);

        Map<String, Object> rels = new java.util.LinkedHashMap<>();
        rels.put("product", productRel);

        ResourceObject ro = new ResourceObject("inventory", s.getProductId(), attrs, rels, links);
        return new DataDocument(links, ro, java.util.List.of());
    }
}