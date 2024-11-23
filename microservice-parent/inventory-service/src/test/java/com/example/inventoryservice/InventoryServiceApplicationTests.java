package com.example.inventoryservice;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

	@ServiceConnection
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
			.withDatabaseName("testdb")
			.withUsername("admin")
			.withPassword("password");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		postgreSQLContainer.start();
	}

	@Test
	void createInventoryTest() {

		String requestBody = """
                {
                    "skuCode": "SKU001",
                    "quantity": 10
                }
                """;

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/inventory")
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("skuCode", Matchers.equalTo("SKU001"))
				.body("quantity", Matchers.equalTo(10));
	}

	@Test
	void isInStockTest() {

		String requestBody = """
                {
                    "skuCode": "SKU001",
                    "quantity": 10
                }
                """;

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/inventory")
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("skuCode", Matchers.equalTo("SKU001"))
				.body("quantity", Matchers.equalTo(10));

		RestAssured.given()
				.contentType("application/json")
				.queryParam("skuCode", "SKU001")
				.queryParam("quantity", 5)
				.when()
				.get("/api/inventory/check")
				.then()
				.log().all()
				.statusCode(200)
				.body(Matchers.equalTo("true"));
	}
}
