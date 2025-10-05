package com.acme.inventory.project.infraestructure.jsonapi;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JsonApiDtoTests {

    @Test
    void errorObject_and_document_holdValues() {
        var eo = new ErrorObject("400", "title", "detail");
        assertThat(eo.status()).isEqualTo("400");
        assertThat(eo.title()).isEqualTo("title");

        var doc = new ErrorDocument(List.of(eo));
        assertThat(doc.errors()).hasSize(1);
    }

    @Test
    void resource_and_data_document() {
        var ro = new ResourceObject("type", "1", null, null, null);
        var dd = new DataDocument(null, ro, null);
        assertThat(dd.data()).isNotNull();
        assertThat(ro.id()).isEqualTo("1");
    }
}
