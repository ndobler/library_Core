package com.santander.games.challenges.quarkus;



import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;

import io.restassured.response.Response;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;




@QuarkusTest
public class BooksResourcesTest {

    @Test
    public void testGet() {
        given()
                .when().get("/books/book/1")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is("{\"id\":1,\"name\":\"Sapiens\",\"publicationYear\":2011}"));
    }

    @Test
    public void testAdd() {

       Response response =given()
                .contentType("application/json")
                .body("{\"id\":null,\"name\":\"La chica mecanica\",\"publicationYear\":2017}")
                .when()
                .post("/books/")
                .then()

                .extract().response();
        System.out.println(response.getBody().toString());
    }

}
