package com.acme.inventory.project.infraestructure.client.products;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import com.acme.inventory.project.application.port.out.FetchProductById;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Component
public class ProductsClient implements FetchProductById {
    /**
     * El cliente REST para comunicarse con el servicio de productos
     */
    private final RestClient client;

    /**
     * Constructor que inyecta el cliente REST
     *
     * @param productsRestClient El cliente REST configurado para el servicio de
     *                           productos
     */
    public ProductsClient(RestClient productsRestClient) {
        this.client = productsRestClient;
    }

    /**
     * Verifica si un producto existe en el servicio de productos
     *
     * @param productId El ID del producto a verificar
     * @return true si el producto existe, false si no existe
     * @throws RestClientResponseException Si ocurre un error al comunicarse con el
     *                                     servicio
     */
    @Override
    @Retry(name = "products")
    @CircuitBreaker(name = "products")
    public boolean exists(String productId) {
        try {
            var resp = client.get()
                    .uri("/api/products/{id}", productId)
                    .retrieve()
                    .toBodilessEntity();
            return resp.getStatusCode().is2xxSuccessful();
        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND)
                return false;
            throw ex;
        }
    }
}