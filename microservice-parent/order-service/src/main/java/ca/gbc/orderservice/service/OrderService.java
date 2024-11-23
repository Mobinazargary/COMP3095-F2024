package ca.gbc.orderservice.service;

import ca.gbc.orderservice.dto.OrderRequest;
import ca.gbc.orderservice.model.Order;

import java.util.List;

public interface OrderService {

    void placeOrder(OrderRequest orderRequest);

    List<Order> getAllOrders();

    Order getOrderById(Long id);

    String updateOrder(Long id, OrderRequest orderRequest);

    void deleteOrder(Long id);


}
