package ca.gbc.orderservice;

import ca.gbc.orderservice.client.InventoryClient;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "inventory.service.url=http://localhost:8080/api/inventory",
        "order-service.version=test-version"
})
class OrderServiceApplicationTests {

    @LocalServerPort
    private int port;

    @MockBean
    private InventoryClient inventoryClient;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.registerParser("text/plain", Parser.TEXT);

        // Mock the InventoryClient responses
        Mockito.when(inventoryClient.isInStock("SKU123", 5)).thenReturn(true);
        Mockito.when(inventoryClient.isInStock("SKU456", 10)).thenReturn(false);
    }

    @Test
    void placeOrderTest() {
        String requestBody = """
                {
                    "skuCode": "SKU123",
                    "price": 100.00,
                    "quantity": 5
                }
                """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/order")
                .then()
                .log().all()
                .statusCode(201)
                .body(equalTo("Order placed successfully"));
    }


    @Test
    void getAllOrdersTest() {
        // Create an order request body
        String orderRequestBody = """
                {
                    "skuCode": "SKU123",
                    "price": 100.00,
                    "quantity": 5
                }
                """;

        // POST - Place a new order
        RestAssured.given()
                .contentType("application/json")
                .body(orderRequestBody)
                .when()
                .post("/api/order")
                .then()
                .statusCode(201)
                .body(Matchers.equalTo("Order placed successfully"));

        // GET - Fetch all orders
        RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/api/order")
                .then()
                .statusCode(200)
                .body("size()", Matchers.greaterThan(0))
                .body("[0].skuCode", Matchers.equalTo("SKU123"))
                .body("[0].quantity", Matchers.equalTo(5))
                .body("[0].price", Matchers.is(100.0F)); // Match the Float representation
    }
}