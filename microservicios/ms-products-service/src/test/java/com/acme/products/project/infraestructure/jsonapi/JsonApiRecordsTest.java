package com.acme.products.project.infraestructure.jsonapi;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonApiRecordsTest {

    @Test
    void error_object_and_document_work() {
        var eo = new ErrorObject("400", "Bad Request", "detail");
        assertEquals("400", eo.status());
        var doc = new ErrorDocument(List.of(eo));
        assertEquals(1, doc.errors().size());
    }

    @Test
    void relationship_object_holds_maps() {
        var rel = new RelationshipObject(java.util.Map.of("self","/x"), java.util.Map.of("id","1"));
        assertEquals("/x", rel.links().get("self"));
        assertEquals("1", rel.data().get("id"));
    }
}
