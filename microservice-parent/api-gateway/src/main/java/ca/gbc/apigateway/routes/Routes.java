package ca.gbc.apigateway.routes;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Slf4j
@Configuration
public class Routes {

    @Value("${services.product.url}")
    private String productServiceUrl;

    @Value("${services.order.url}")
    private String orderServiceUrl;

    @Value("${services.inventory.url}")
    private String inventoryServiceUrl;


//    @Bean
//    public RouterFunction<ServerResponse> productServiceRoute() {

//        log.info("Initializing product service route with URL: {}", productServiceUrl);

//        return GatewayRouterFunctions.route("product_service")
//                .route(RequestPredicates.path("/api/product"), request -> {

//                    log.info("Recieved request for product service: {}", request.uri());
//                    try {
//                        ServerResponse response = HandlerFunctions.http(productServiceUrl).handle(request);
//                        log.info("Response status: {}", response.statusCode());
//                        return response;
//                    } catch(Exception e) {
//                        log.error("Error occurred while routing Request: {}", e.getMessage(), e);
//                        return ServerResponse.status(500).body("An error occurred routing");
//                    }
//                })
//                .build();
    //   }

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        log.info("Initializing product service route with URL: {}", productServiceUrl);

        return GatewayRouterFunctions.route("product_service")
                .route(RequestPredicates.path("/api/product/**"), request -> {
                    log.info("Received request for product service: {}", request.uri());

                    try {
                        // Forward the full path, appending the dynamic path (e.g., /{id}) to the service URL
                        String forwardPath = productServiceUrl + request.uri().getPath();
                        log.info("Forwarding to: {}", forwardPath);

                        ServerResponse response = HandlerFunctions.http(forwardPath).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    } catch (Exception e) {
                        log.error("Error occurred while routing Request: {}", e.getMessage(), e);
                        return ServerResponse.status(500).body("An error occurred routing the request");
                    }
                })
                .build();
    }



    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {

        log.info("Initializing order service route with URL: {}", orderServiceUrl);

        return GatewayRouterFunctions.route("order_service")
                .route(RequestPredicates.path("/api/order"), request -> {

                    log.info("Recieved request for order service: {}", request.uri());
                    try {
                        ServerResponse response = HandlerFunctions.http(orderServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    } catch(Exception e) {
                        log.error("Error occurred while routing Request: {}", e.getMessage(), e);
                        return ServerResponse.status(500).body("An error occurred routing");
                    }
                })
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute() {

        log.info("Initializing order service route with URL: {}", inventoryServiceUrl);

        return GatewayRouterFunctions.route("inventory_service")
                .route(RequestPredicates.path("/api/inventory"), request -> {

                    log.info("Recieved request for inventory service: {}", request.uri());
                    try {
                        ServerResponse response = HandlerFunctions.http(inventoryServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    } catch(Exception e) {
                        log.error("Error occurred while routing Request: {}", e.getMessage(), e);
                        return ServerResponse.status(500).body("An error occurred routing");
                    }
                })
                .build();
    }


    @Bean
    public RouterFunction<ServerResponse> productServiceSwaggerRoute() {

        return GatewayRouterFunctions.route("product_service_swagger")
                .route(RequestPredicates.path("/aggregate/product-service/v3/api-docs"),
                        HandlerFunctions.http(productServiceUrl))
                .filter(setPath("/v3/api-docs"))
                .build();


    }


    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoute() {

        return GatewayRouterFunctions.route("order_service_swagger")
                .route(RequestPredicates.path("/aggregate/order-service/v3/api-docs"),
                        HandlerFunctions.http(orderServiceUrl))
                .filter(setPath("/v3/api-docs"))
                .build();


    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute() {

        return GatewayRouterFunctions.route("inventory_service_swagger")
                .route(RequestPredicates.path("/aggregate/inventory-service/v3/api-docs"),
                        HandlerFunctions.http(inventoryServiceUrl))
                .filter(setPath("/v3/api-docs"))
                .build();


    }


}
