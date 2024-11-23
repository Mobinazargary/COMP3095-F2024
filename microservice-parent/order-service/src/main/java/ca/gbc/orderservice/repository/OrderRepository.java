package ca.gbc.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.gbc.orderservice.model.Order;
public interface OrderRepository extends JpaRepository<Order, Long> {
}
