package com.acme.products;

import org.junit.jupiter.api.Test;

class ProductsServiceApplicationMainTest {

    @Test
    void main_starts() {
        // run main but disable web server to avoid binding to port during tests
        ProductsServiceApplication.main(new String[]{"--spring.main.web-application-type=none"});
    }
}
