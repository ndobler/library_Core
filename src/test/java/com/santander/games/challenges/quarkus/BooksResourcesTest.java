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

        //Must not fail Bad Request
        given()
                .when().get("/books/book/pipo")
                .then()
                .statusCode(400);
    }

    @Test
    public void testAdd() {

       given()
                .contentType("application/json")
                .body("{\"id\":null,\"name\":\"La chica mecanica\",\"publicationYear\":2017}")
                .when()
                .post("/books/")
                .then()
                .statusCode(201)
                .body(CoreMatchers.is("{\"id\":100,\"name\":\"La chica mecanica\",\"publicationYear\":2017}"));

       //Must no duplicate or fail
        given()
                .contentType("application/json")
                .body("{\"id\":null,\"name\":\"La chica mecanica\",\"publicationYear\":2017}")
                .when()
                .post("/books/")
                .then()
                .statusCode(201)
                .body(CoreMatchers.is("{\"id\":100,\"name\":\"La chica mecanica\",\"publicationYear\":2017}"));


        //Must no fail Bad Request
        given()
                .contentType("application/json")
                .body("{\"id\":10,\"name\":\"chica mecanica\",\"publicationYear\":2017}")
                .when()
                .post("/books/")
                .then()
                .statusCode(400);


    }

    @Test
    public void testAll() {

     given()
                .when().get("/books/all")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is("[{\"id\":1,\"name\":\"Sapiens\",\"publicationYear\":2011},{\"id\":2,\"name\":\"Homo Deus\",\"publicationYear\":2015},{\"id\":3,\"name\":\"Enlightenment Now\",\"publicationYear\":2018},{\"id\":4,\"name\":\"Factfulness\",\"publicationYear\":2018},{\"id\":5,\"name\":\"Sleepwalkers\",\"publicationYear\":2012},{\"id\":6,\"name\":\"The Silk Roads\",\"publicationYear\":2015},{\"id\":7,\"name\":\"The culture map\",\"publicationYear\":2016},{\"id\":8,\"name\":\"Billy Summers\",\"publicationYear\":2021},{\"id\":9,\"name\":\"The Handmaids Tale\",\"publicationYear\":2016},{\"id\":10,\"name\":\"The Institue\",\"publicationYear\":2019},{\"id\":100,\"name\":\"La chica mecanica\",\"publicationYear\":2017}]"));;

    }


    @Test
    public void testList() {

        given()
                .when().get("/books/list")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is("[{\"id\":1,\"name\":\"Sapiens\",\"publicationYear\":2011},{\"id\":2,\"name\":\"Homo Deus\",\"publicationYear\":2015},{\"id\":3,\"name\":\"Enlightenment Now\",\"publicationYear\":2018},{\"id\":4,\"name\":\"Factfulness\",\"publicationYear\":2018},{\"id\":5,\"name\":\"Sleepwalkers\",\"publicationYear\":2012}]"));;

    }

    @Test
    public void testByName(){
        given()
                .when().get("/books/byName/Factfulness")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is("{\"id\":4,\"name\":\"Factfulness\",\"publicationYear\":2018}"));

    }

    @Test
    public void testModify(){

        given()
                .when().get("/books/byName/La chica mecanica")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is("{\"id\":100,\"name\":\"La chica mecanica\",\"publicationYear\":2017}"));
        given().contentType("application/json")
                .body("{\"id\":100,\"name\":\"La chica mecanica\",\"publicationYear\":2019}")
                .when()
                .put("/books/")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is("{\"id\":100,\"name\":\"La chica mecanica\",\"publicationYear\":2019}"));

        given()
                .when().get("/books/byName/La chica mecanica")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is("{\"id\":100,\"name\":\"La chica mecanica\",\"publicationYear\":2019}"));

    }

    @Test
    public void testByPublicationYearBetween(){
        given()
                .when().get("/books/byPublicationYearBetween/2016/2020")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is("[{\"id\":7,\"name\":\"The culture map\",\"publicationYear\":2016},{\"id\":9,\"name\":\"The Handmaids Tale\",\"publicationYear\":2016},{\"id\":100,\"name\":\"La chica mecanica\",\"publicationYear\":2017},{\"id\":3,\"name\":\"Enlightenment Now\",\"publicationYear\":2018},{\"id\":4,\"name\":\"Factfulness\",\"publicationYear\":2018},{\"id\":10,\"name\":\"The Institue\",\"publicationYear\":2019}]"));
    }

    @Test
    void testDelete(){
        given()
                .contentType("application/json")
                .body("{\"id\":null,\"name\":\"Así hablo Zaratrusta\",\"publicationYear\":1883}")
                .when()
                .post("/books/")
                .then()
                .statusCode(201)
                .body(CoreMatchers.is("{\"id\":101,\"name\":\"Así hablo Zaratrusta\",\"publicationYear\":1883}"));

        given()
                .contentType("application/json")
                .body("{\"id\":101,\"name\":\"Así hablo Zaratrusta\",\"publicationYear\":1883}")
                .when()
                .delete("/books/")
                .then()
                .statusCode(204);

        given()
                .when().get("/books/byName/Así hablo Zaratrusta")
                .then()
                .statusCode(404);

        // must not fail
        given()
                .contentType("application/json")
                .body("{\"id\":101,\"name\":\"Así hablo Zaratrusta\",\"publicationYear\":1883}")
                .when()
                .delete("/books/")
                .then()
                .statusCode(204);

    }


}
