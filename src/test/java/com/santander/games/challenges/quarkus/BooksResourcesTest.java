package com.santander.games.challenges.quarkus;


import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import static io.restassured.RestAssured.given;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;


@QuarkusTestResource(H2DatabaseTestResource.class)
public class BooksResourcesTest {

    @Test
    public void testGet() {
        given()
                .when().get("/books/book/1")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is("{}"));
    }
}
