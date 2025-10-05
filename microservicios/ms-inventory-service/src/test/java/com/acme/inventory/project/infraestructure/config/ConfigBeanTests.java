package com.acme.inventory.project.infraestructure.config;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigBeanTests {

    @Test
    void openApiBean_created() {
        var cfg = new OpenApiConfig();
        var api = cfg.api();
        assertThat(api).isNotNull();
        assertThat(api.getInfo().getTitle()).contains("Inventory");
    }

    @Test
    void productsProperties_settersAndTimeouts() {
        var p = new InventoryProductsProperties();
        p.setBaseUrl("http://localhost");
        p.setApiKey("k");
        p.setApiKeyHeader("X-API-KEY");
        p.getTimeouts().setConnectMillis(200);
        p.getTimeouts().setReadMillis(500);

        assertThat(p.getBaseUrl()).isEqualTo("http://localhost");
        assertThat(p.getTimeouts().getConnectMillis()).isEqualTo(200);
    }

    @Test
    void restClientBean_builds() {
        var rc = new RestClientConfig();
        var p = new InventoryProductsProperties();
        p.setBaseUrl("http://localhost");
        p.setApiKey("abc");
        p.setApiKeyHeader("X-API-KEY");
        p.getTimeouts().setConnectMillis(10);
        p.getTimeouts().setReadMillis(10);

        var client = rc.productsRestClient(p);
        assertThat(client).isNotNull();
    }
}
