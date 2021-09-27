package com.santander.games.challenges.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class BooksResourcesTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/books/book/1")
                .then()
                .statusCode(200)
                .body("{}");
    }
}
