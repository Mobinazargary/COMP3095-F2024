package ca.gbc.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // Use this if using Feign

@SpringBootApplication
@EnableFeignClients(basePackages = "ca.gbc.orderservice.client") // Declarative REST Client
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
