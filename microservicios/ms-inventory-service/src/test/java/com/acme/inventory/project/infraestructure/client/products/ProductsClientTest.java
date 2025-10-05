package com.acme.inventory.project.infraestructure.client.products;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductsClientTest {

    @Test
    void testExists_whenProductExists() {
        RestClient client = mock(RestClient.class);
        RestClient.RequestHeadersUriSpec uriSpec = mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec headersSpec = mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(client.get()).thenReturn(uriSpec);
        when(uriSpec.uri("/api/products/{id}", "1")).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        ResponseEntity<Void> resp = new ResponseEntity<>(HttpStatus.OK);
        when(responseSpec.toBodilessEntity()).thenReturn(resp);

        ProductsClient pc = new ProductsClient(client);
        assertTrue(pc.exists("1"));
    }

    @Test
    void testExists_whenProductNotFound() {
        RestClient client = mock(RestClient.class);
        RestClient.RequestHeadersUriSpec uriSpec = mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec headersSpec = mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(client.get()).thenReturn(uriSpec);
        when(uriSpec.uri("/api/products/{id}", "1")).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        RestClientResponseException notFound = new RestClientResponseException("Not Found", HttpStatus.NOT_FOUND.value(), "Not Found", null, null, null);
        when(responseSpec.toBodilessEntity()).thenThrow(notFound);

        ProductsClient pc = new ProductsClient(client);
        assertFalse(pc.exists("1"));
    }

    @Test
    void testExists_whenServerError_throws() {
        RestClient client = mock(RestClient.class);
        RestClient.RequestHeadersUriSpec uriSpec = mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec headersSpec = mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(client.get()).thenReturn(uriSpec);
        when(uriSpec.uri("/api/products/{id}", "1")).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        RestClientResponseException serverErr = new RestClientResponseException("Server", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Err", null, null, null);
        when(responseSpec.toBodilessEntity()).thenThrow(serverErr);

        ProductsClient pc = new ProductsClient(client);
        try {
            pc.exists("1");
            throw new AssertionError("expected exception");
        } catch (RestClientResponseException ex) {
            // expected
        }
    }
}
