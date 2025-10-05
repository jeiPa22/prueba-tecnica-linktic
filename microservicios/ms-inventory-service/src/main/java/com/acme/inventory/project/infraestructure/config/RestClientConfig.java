package com.acme.inventory.project.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

/**
 * Configuración del cliente REST para comunicarse con otros servicios.
 */
@Configuration
public class RestClientConfig {
    /**
     * Configura un RestClient para comunicarse con el servicio de productos.
     * 
     * @param props Las propiedades de configuración del servicio de productos.
     * @return Un RestClient configurado para comunicarse con el servicio de
     *         productos.
     */
    @Bean
    RestClient productsRestClient(InventoryProductsProperties props) {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(props.getTimeouts().getConnectMillis()));
        factory.setReadTimeout(Duration.ofMillis(props.getTimeouts().getReadMillis()));
        return RestClient.builder()
                .baseUrl(props.getBaseUrl())
                .requestFactory(factory)
                .defaultHeaders(h -> h.set(props.getApiKeyHeader(), props.getApiKey()))
                .build();
    }
}