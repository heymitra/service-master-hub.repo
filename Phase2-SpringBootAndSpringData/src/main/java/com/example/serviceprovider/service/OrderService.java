package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order, Long subServiceId, Long customerId);
    Order update(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    void deleteById(Long orderId);
    List<Order> getAvailableOrdersByExpert(Long expertId);
    Order makePayment(Order order);
    Order completeOrder (Order order);
}
