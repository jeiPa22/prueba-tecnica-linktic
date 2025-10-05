package com.acme.inventory.src.infraestructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    /**
     * Configura y proporciona un RestClient para comunicarse con el servicio 
     * 
     * @param baseUrl       la URL base del servicio de productos
     * @param apiKeyHeader  el nombre del encabezado para la clave API
     * @param apiKey        la clave API para autenticación
     * @param connectMillis el tiempo máximo de conexión en milisegundos
     * @param readMillis    el tiempo máximo de lectura en milisegundos
     * @return un RestClient configurado para comunicarse con el servicio de
     *         productos
     */
    @Bean
    RestClient productsRestClient(
            @Value("${inventory.products.baseUrl}") String baseUrl,
            @Value("${inventory.products.apiKeyHeader}") String apiKeyHeader,
            @Value("${inventory.products.apiKey}") String apiKey,
            @Value("${inventory.products.timeouts.connectMillis}") int connectMillis,
            @Value("${inventory.products.timeouts.readMillis}") int readMillis) {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(connectMillis));
        factory.setReadTimeout(Duration.ofMillis(readMillis));
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .defaultHeaders(h -> h.set(apiKeyHeader, apiKey))
                .build();
    }
}