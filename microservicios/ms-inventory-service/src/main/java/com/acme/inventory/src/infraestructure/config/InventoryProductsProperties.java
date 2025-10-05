package com.acme.inventory.src.infraestructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades de configuración para el servicio de productos.
 */
@ConfigurationProperties("inventory.products")
public class InventoryProductsProperties {
    /**
     * La URL base del servicio de productos.
     */
    private String baseUrl;
    /**
     * El nombre del encabezado HTTP que contiene la clave de API.
     */
    private String apiKeyHeader;
    /**
     * La clave de API para autenticar las solicitudes al servicio de productos.
     */
    private String apiKey;
    /**
     * Los tiempos de espera para las solicitudes al servicio de productos.
     */
    private final Timeouts timeouts = new Timeouts();

    /**
     * Los tiempos de espera para las solicitudes al servicio de productos.
     */
    public static class Timeouts {
        private int connectMillis;
        private int readMillis;

        public int getConnectMillis() {
            return connectMillis;
        }

        public void setConnectMillis(int connectMillis) {
            this.connectMillis = connectMillis;
        }

        public int getReadMillis() {
            return readMillis;
        }

        public void setReadMillis(int readMillis) {
            this.readMillis = readMillis;
        }
    }

    /**
     * Obtiene la URL base del servicio de productos.
     *
     * @return La URL base del servicio de productos.
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Establece la URL base del servicio de productos.
     *
     * @param baseUrl La URL base del servicio de productos.
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Obtiene el nombre del encabezado HTTP que contiene la clave de API.
     *
     * @return El nombre del encabezado HTTP que contiene la clave de API.
     */
    public String getApiKeyHeader() {
        return apiKeyHeader;
    }

    /**
     * Establece el nombre del encabezado HTTP que contiene la clave de API.
     *
     * @param apiKeyHeader El nombre del encabezado HTTP que contiene la clave de
     *                     API.
     */
    public void setApiKeyHeader(String apiKeyHeader) {
        this.apiKeyHeader = apiKeyHeader;
    }

    /**
     * Obtiene la clave de API para autenticar las solicitudes al servicio de
     * productos.
     *
     * @return La clave de API para autenticar las solicitudes al servicio de
     *         productos.
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Establece la clave de API para autenticar las solicitudes al servicio de
     * productos.
     *
     * @param apiKey La clave de API para autenticar las solicitudes al servicio de
     *               productos.
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Obtiene los tiempos de espera para las solicitudes al servicio de productos.
     *
     * @return Los tiempos de espera para las solicitudes al servicio de productos.
     */
    public Timeouts getTimeouts() {
        return timeouts;
    }
}
