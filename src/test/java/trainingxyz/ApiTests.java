package trainingxyz;

import io.restassured.response.ValidatableResponse;
import models.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    @Test
    public void getCategories() {
        String endpoint = "http://localhost:8888/api_testing/category/read.php";
        ValidatableResponse response = given().when().get(endpoint).then();
        response.log().body();
    }

    @Test
    public void getProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        //  ValidatableResponse response = given().queryParam("id", 2).when().get(endpoint).then();
        //response.log().body();
        // given().queryParam("id", 2).when().get(endpoint).then().log().body();
       // given().queryParam("id", 2).when().get(endpoint).then().log().all();
        // given().queryParam("id", 2).when().get(endpoint).then().assertThat().statusCode(200);
        // given().queryParam("id", 2).when().get(endpoint).then().assertThat().statusCode(201);

//        given().queryParam("id", 2).when().get(endpoint).then().assertThat()
//                .statusCode(200)
//                .body("id", equalTo("2"))
//                .body("name", equalTo("Cross-Back Training Tank"))
//                .body("description", equalTo("The most awesome phone of 2013!"))
//                .body("price", equalTo("299.00"))
//                .body("category_id", equalTo("2"))
//                .body("category_name", equalTo("Active Wear - Women"));
    }

    @Test
    public void createProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        String body = "{\"name\" :  \"Water bottle\", \"description\": \"Blue water bottle. Holds 64 ounces\", \"price\": \"12\", \"category_id\": \"3\"}";
        ValidatableResponse response = given().body(body).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void updateProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/update.php";
        String body = "{\"id\" :  \"1000\", \"name\" :  \"Water bottle\", \"description\": \"Blue water bottle. Holds 64 ounces\", \"price\": \"15\", \"category_id\": \"3\"}";
        ValidatableResponse response = given().body(body).when().put(endpoint).then();
        response.log().body();
    }

    @Test
    public void deleteProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/delete.php";
        String body = "{\"id\" :  \"1000\"}";
        ValidatableResponse response = given().body(body).when().delete(endpoint).then();
        response.log().body();
    }

    @Test
    public void createSerializedProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        Product product = new Product("Water bottle", "Blue water bottle. Holds 64 ounces", 15, 3);
        ValidatableResponse response = given().body(product).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void createSerializedProductSweatBand() {
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        Product product = new Product("Sweatband", "It is a sweatband", 5, 3);
        ValidatableResponse response = given().body(product).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void retreiveSerializedProductSweatBand() {
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        ValidatableResponse response = given().queryParam("id", 1007).when().get(endpoint).then();
        response.log().body();
    }

    @Test
    public void updateSerializedProductSweatBand() {
        String endpoint = "http://localhost:8888/api_testing/product/update.php";
        Product product = new Product(1007, "Sweatband", "It is a sweatband", 6,
                3, "Active Wear - Unisex"
        );
        ValidatableResponse response = given().body(product).when().put(endpoint).then();
        response.log().body();
    }

    @Test
    public void deleteProductSweatBand() {
        String endpoint = "http://localhost:8888/api_testing/product/delete.php";
        String body = "{\"id\" :  \"1007\"}";
        ValidatableResponse response = given().body(body).when().delete(endpoint).then();
        response.log().body();
    }

    @Test
    public void getProducts() {
        String endpoint = "http://localhost:8888/api_testing/product/read.php";
        given()
                .when()
                .get(endpoint)
                .then()
                .log()
                .headers()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=UTF-8"))
                .body("records.size()", greaterThan(0))
                .body("records.id", everyItem(notNullValue()))
                .body("records.name", everyItem(notNullValue()))
                .body("records.description", everyItem(notNullValue()))
                .body("records.price", everyItem(notNullValue()))
                .body("records.category_id", everyItem(notNullValue()))
                .body("records.category_name", everyItem(notNullValue()))
                .body("records.id[0]", equalTo("1001"));
    }

    @Test
    public void getDeserializedProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        Product expectedProduct = new Product(2, "Cross-Back Training Tank",
                "The most awesome phone of 2013!", 299.00, 2,
                "Active Wear - Women");

        Product actualProduct = given().queryParam("id", "2").when().get(endpoint).as(Product.class);

        assertThat(actualProduct, samePropertyValuesAs(expectedProduct));
    }

    @Test
    public void createMultiVitaminProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        Product product = new Product("Multi Vitamin", "Good for your health!", 20, 4);
        ValidatableResponse response = given().body(product).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void retreiveMultiVitaminProduct() {
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";

        given().
                queryParam("id", 1005).
                when()
                .get(endpoint)
                .then().
                assertThat().
                statusCode(200)
                .header("Content-Type", equalTo("application/json"))
                .body("id", equalTo("1005"))
                .body("name", equalTo("Multi Vitamin"))
                .body("description", equalTo("Good for your health!"))
                .body("price", equalTo("20.00"))
                .body("category_id", equalTo("4"))
                .body("category_name", equalTo("Supplements"));
    }

}
