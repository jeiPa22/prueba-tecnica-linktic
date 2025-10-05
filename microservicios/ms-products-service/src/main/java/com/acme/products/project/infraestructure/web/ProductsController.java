package com.acme.products.project.infraestructure.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acme.products.project.application.port.in.CreateProduct;
import com.acme.products.project.application.port.in.GetProduct;
import com.acme.products.project.application.port.in.ListProducts;
import com.acme.products.project.application.port.in.UpdateProduct;
import com.acme.products.project.domain.model.Product;
import com.acme.products.project.infraestructure.jsonapi.DataDocument;
import com.acme.products.project.infraestructure.jsonapi.RelationshipObject;
import com.acme.products.project.infraestructure.jsonapi.ResourceObject;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Controlador REST para gestionar productos.
 * Proporciona endpoints para crear, obtener, actualizar y listar productos
 * utilizando el formato JSON:API.
 */
@RestController
@RequestMapping(value = "/api/v1/products", produces = "application/vnd.api+json")
public class ProductsController {

    /**
     * Servicio para crear productos
     */
    private final CreateProduct create;
    /**
     * Servicio para obtener productos
     */
    private final GetProduct get;
    /**
     * Servicio para actualizar productos
     */
    private final UpdateProduct update;
    /**
     * Servicio para listar productos
     */
    private final ListProducts list;

    /**
     * Constructor del controlador de productos.
     * 
     * @param create Servicio para crear productos
     * @param get    Servicio para obtener productos
     * @param update Servicio para actualizar productos
     * @param list   Servicio para listar productos
     */
    public ProductsController(CreateProduct create, GetProduct get, UpdateProduct update, ListProducts list) {
        this.create = create;
        this.get = get;
        this.update = update;
        this.list = list;
    }

    /**
     * Crea un nuevo producto.
     * 
     * @param body Cuerpo de la solicitud con los datos del producto en formato
     *             JSON:API
     * @return Respuesta con el producto creado en formato JSON:API
     */
    @PostMapping(consumes = "application/vnd.api+json")
    public ResponseEntity<DataDocument> create(@RequestBody Map<String, Object> body) {
        // JSON:API: data.attributes
        Object dataObj = body.get("data");
        if (!(dataObj instanceof Map)) {
            throw new IllegalArgumentException("Formato de datos inválido");
        }
        Map<?, ?> data = (Map<?, ?>) dataObj;

        Object attrsObj = data.get("attributes");
        if (!(attrsObj instanceof Map)) {
            throw new IllegalArgumentException("Formato de atributos inválido");
        }
        Map<?, ?> attrs = (Map<?, ?>) attrsObj;

        String name = null;
        BigDecimal price = null;
        String currency = null;
        String status = null;

        Object nameObj = attrs.get("name");
        if (nameObj != null) {
            name = nameObj.toString();
        }

        Object priceObj = attrs.get("price");
        if (priceObj != null) {
            price = new BigDecimal(priceObj.toString());
        }

        Object currencyObj = attrs.get("currency");
        if (currencyObj != null) {
            currency = currencyObj.toString().toUpperCase();
        }

        Object statusObj = attrs.get("status");
        if (statusObj != null) {
            status = statusObj.toString().toUpperCase();
        }

        var now = Instant.now();
        var p = new Product(UUID.randomUUID().toString(), name, price, currency, status, now, now);
        var created = create.create(p);
        return ResponseEntity.status(200).body(toDocument(created));
    }

    /**
     * Obtiene un producto por su ID.
     * 
     * @param id ID del producto a obtener
     * @return Respuesta con el producto encontrado en formato JSON:API
     */
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<DataDocument> get(@PathVariable String id) {
        var p = get.byId(id);
        return ResponseEntity.ok(toDocument(p));
    }

    /**
     * Actualiza un producto existente.
     * 
     * @param id   ID del producto a actualizar
     * @param body Cuerpo de la solicitud con los datos del producto en formato
     *             JSON:API
     * @return Respuesta con el producto actualizado en formato JSON:API
     */
    @PatchMapping(value = "/{id}", consumes = "application/vnd.api+json")
    public ResponseEntity<DataDocument> patch(@PathVariable String id, @RequestBody Map<String, Object> body) {
        Object dataObj = body.get("data");
        if (!(dataObj instanceof Map)) {
            throw new IllegalArgumentException("Formato de datos inválido");
        }
        Map<?, ?> data = (Map<?, ?>) dataObj;

        Object attrsObj = data.get("attributes");
        Map<?, ?> attrs = null;
        if (attrsObj != null) {
            if (!(attrsObj instanceof Map)) {
                throw new IllegalArgumentException("Formato de atributos inválido");
            }
            attrs = (Map<?, ?>) attrsObj;
        }

        String name = attrs == null ? null : (attrs.get("name") == null ? null : attrs.get("name").toString());
        BigDecimal price = null;
        if (attrs != null && attrs.get("price") != null) {
            price = new BigDecimal(attrs.get("price").toString());
        }
        String currency = attrs == null ? null : (String) attrs.get("currency");
        String status = attrs == null ? null : (String) attrs.get("status");

        var updated = update.update(id, name, price, currency, status);
        return ResponseEntity.ok(toDocument(updated));
    }

    /**
     * Lista productos con paginación y filtros opcionales.
     * 
     * @param pageNum  Número de página (por defecto 1)
     * @param pageSize Tamaño de página (por defecto 10)
     * @param name     Filtro por nombre (opcional)
     * @param status   Filtro por estado (opcional)
     * @param host     Encabezado Host de la solicitud (opcional)
     * @return Respuesta con la lista de productos en formato JSON:API
     */
    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<DataDocument> list(
            @RequestParam(name = "page[number]", defaultValue = "1") int pageNum,
            @RequestParam(name = "page[size]", defaultValue = "10") int pageSize,
            @RequestParam(name = "filter[name]", required = false) String name,
            @RequestParam(name = "filter[status]", required = false) String status,
            @RequestHeader(value = "Host", required = false) String host) {

        int p = Math.max(1, pageNum) - 1;
        int s = Math.max(1, pageSize);
        Pageable pageable = PageRequest.of(p, s);
        var result = list.list(name, status, pageable);

        var self = "/api/v1/products?page[number]=" + (p + 1) + "&page[size]=" + s
                + (name != null ? "&filter[name]=" + name : "")
                + (status != null ? "&filter[status]=" + status : "");
        var links = new java.util.LinkedHashMap<String, String>();
        links.put("self", self);
        if (result.hasNext()) {
            links.put("next", "/api/v1/products?page[number]=" + (p + 2) + "&page[size]=" + s
                    + (name != null ? "&filter[name]=" + name : "")
                    + (status != null ? "&filter[status]=" + status : ""));
        }
        if (result.hasPrevious()) {
            links.put("prev", "/api/v1/products?page[number]=" + (p) + "&page[size]=" + s
                    + (name != null ? "&filter[name]=" + name : "")
                    + (status != null ? "&filter[status]=" + status : ""));
        }

        List<Object> included = java.util.List.of();

        var meta = new java.util.LinkedHashMap<String, Object>();
        meta.put("totalElements", Long.valueOf(result.getTotalElements()));
        meta.put("totalPages", Integer.valueOf(result.getTotalPages()));
        meta.put("page", Integer.valueOf(p + 1));
        meta.put("size", Integer.valueOf(s));

        var dataList = result.getContent().stream().map(this::toResource).toList();
        var doc = new DataDocument(links, dataList, included, meta);
        return ResponseEntity.ok(doc);
    }

    /**
     * Convierte un producto a un documento JSON:API.
     * 
     * @param p Producto a convertir
     * @return Documento JSON:API con el producto
     */
    private DataDocument toDocument(Product p) {
        var links = Map.of("self", "/api/v1/products/" + p.getId());
        Map<String, Object> meta = Map.of();
        return new DataDocument(links, List.of(toResource(p)), List.of(), meta);
    }

    /**
     * Convierte un producto a un objeto de recurso JSON:API.
     * 
     * @param p Producto a convertir
     * @return Objeto de recurso JSON:API con el producto
     */
    private ResourceObject toResource(Product p) {
        var attrs = new java.util.LinkedHashMap<String, Object>();
        attrs.put("name", p.getName());
        attrs.put("price", p.getPrice());
        attrs.put("currency", p.getCurrency());
        attrs.put("status", p.getStatus());
        attrs.put("createdAt", p.getCreatedAt().toString());
        attrs.put("updatedAt", p.getUpdatedAt().toString());

        Map<String, RelationshipObject> rels = Map.of();
        Map<String, String> links = Map.of("self", "/api/v1/products/" + p.getId());

        return new ResourceObject("products", p.getId(), attrs, rels, links);
    }
}