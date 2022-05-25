package com.cocktail.api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class searchIngredientByNameTest {

    private static final String BASE_URI = "https://www.thecocktaildb.com/api/json/v1/1/search.php";

    @Test(description = "Search by valid uppercase ingredient name returns ingredient", priority = 0)
    public void searchByIngredientName_UppercaseName() {

        String ingredientName = "VODKA";

        Response response = RestAssured.given()
                .queryParam("i", ingredientName)
                .log().all()
                .when()
                .get(BASE_URI);
        JsonPath jsPath = response.jsonPath();

        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Response status code is 200 ok");
        Assert.assertNotNull(jsPath.get("ingredients"), "Ingredients is not null");
    }

    @Test(description = "Search by valid lowercase ingredient name returns ingredient", priority = 1)
    public void searchByIngredientName_LowercaseName() {

            String ingredientName = "vodka";

            Response response = RestAssured.given()
                    .queryParam("i", ingredientName)
                    .log().all()
                    .when()
                    .get(BASE_URI);
            JsonPath jsPath = response.jsonPath();

            Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Response status code is 200 ok");
            Assert.assertNotNull(jsPath.get("ingredients"), "Ingredients is not null");
    }

    @Test(description = "Search by valid alcoholic ingredient name returns alcoholic properties", priority = 2)
    public void searchByIngredientName_Alcoholic() {

        String ingredientName = "vodka";

        Response response = RestAssured.given()
                .queryParam("i", ingredientName)
                .log().all()
                .when()
                .get(BASE_URI);
        JsonPath respJSPath = response.jsonPath();

        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Response status code is 200 ok");
        Assert.assertNotNull(respJSPath.get("ingredients"), "Ingredients is not null");
        Assert.assertEquals(respJSPath.get("ingredients[0].strAlcohol"), "Yes", "Ingredients is not null");
        Assert.assertNotNull(respJSPath.get("ingredients[0].strABV"), "Ingredients is not null");

    }

    @Test(description = "Search by valid non-alcoholic ingredient name returns non-alcoholic properties", priority = 3)
    public void searchByIngredientName_NonAlcoholic() {

        String ingredientName = "strawberry";

        Response response = RestAssured.given()
                .queryParam("i", ingredientName)
                .log().all()
                .when()
                .get(BASE_URI);
        JsonPath respJSPath = response.jsonPath();

        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Response status code is 200 ok");
        Assert.assertNotNull(respJSPath.get("ingredients"), "Ingredients is not null");
        Assert.assertNull(respJSPath.get("ingredients[0].strAlcohol"), "strAlcohol is null");
        Assert.assertNull(respJSPath.get("ingredients[0].strABV"), "strABV is null");

    }

    @Test(description = "Search by valid non-alcoholic ingredient name returns non-alcoholic properties", priority = 5)
    public void searchByIngredientName_NotFound() {

        String ingredientName = "invalid_ingredient";

        Response response = RestAssured.given()
                .queryParam("i", ingredientName)
                .log().all()
                .when()
                .get(BASE_URI);
        JsonPath respJSPath = response.jsonPath();

        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Response status code is 200 ok");
        Assert.assertNull(respJSPath.get("ingredients"), "ingredients property is null");

    }
}
