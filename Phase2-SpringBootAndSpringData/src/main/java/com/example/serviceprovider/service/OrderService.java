package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order saveOrder(Order order);
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    void deleteById(Long orderId);
    List<Order> getOrdersForExpert(Expert expert);
    void startOrder(Order order);
    void completeOrder(Order order);
}
