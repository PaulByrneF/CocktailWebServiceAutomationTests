package com.cocktail.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;

public class searchCocktailByNameTest {

    private static final String BASE_URI = "https://www.thecocktaildb.com/api/json/v1/1/search.php";

    @Test(description = "Search by valid uppercase cocktail name returns cocktails", priority = 0)
    public void searchByCocktailName_UppercaseName() {

        String cocktailName = "MARGARITA";

        Response response = RestAssured.given()
                .queryParam("s", cocktailName)
                .log().all()
                .when()
                .get(BASE_URI);
        JsonPath respJSPath = response.jsonPath();

        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Response status code is 200 OK");
        Assert.assertFalse(respJSPath.getList("drinks").isEmpty(), "Drinks Array is not empty");
    }

    @Test(description = "Search by valid lowercase cocktail name returns cocktails", priority = 1)
    public void searchByCocktailName_LowercaseName() {

        String cocktailName = "margarita";

        Response response = RestAssured.given()
                .queryParam("s", cocktailName)
                .log().all()
                .when()
                .get(BASE_URI);
        JsonPath respJSPath = response.jsonPath();

        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Response status code is 200 OK");
        Assert.assertFalse(respJSPath.getList("drinks").isEmpty(), "Drinks Array is not empty");
    }

    @Test(description = "Search by invalid cocktail name returns null drinks property", priority = 2)
    public void searchByCocktailName_CocktailNotFound() {

        String cocktailName = "invalid_cocktail";

        Response response = RestAssured.given()
                .queryParam("s", cocktailName)
                .log().all()
                .when()
                .get(BASE_URI);
        JsonPath respJSPath = response.jsonPath();

        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Response status code is 200 OK");
        Assert.assertNull(respJSPath.get("drinks"), "Drinks Array is not empty");
    }

    @Test(description = "Search by cocktail name returns valid payload schema", priority = 3)
    public void searchByCocktailName_SchemaValidation() {

        String cocktailName = "margarita";

        Response response = RestAssured.given()
                .queryParam("s", cocktailName)
                .log().all()
                .when()
                .get(BASE_URI);

        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Response status code is 200 OK");
        Assert.assertEquals(response.contentType(), ContentType.JSON.toString(), "Response returned as JSON");
        MatcherAssert.assertThat(response.getBody().asString(), JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/getCocktailsByName.json"));
    }
}
