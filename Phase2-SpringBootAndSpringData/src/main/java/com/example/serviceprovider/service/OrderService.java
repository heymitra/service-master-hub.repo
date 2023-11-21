package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Order;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order, Long subServiceId, Long customerId);
    Order update(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    void deleteById(Long orderId);
    List<Order> getAvailableOrdersByExpert();
    Order makePayment(Order order);
    Order completeOrder (Long orderId);
    Order start (Long orderId);
    Page<Order> searchAndFilterOrders(String status, Pageable pageable);
    List<Order> getOrdersByCustomerAndStatus(OrderStatusEnum status);
    List<Order> getOrdersByUser(long userId);
    List<Order> getOrdersByCriteria(LocalDateTime startTime,
                                    LocalDateTime endTime,
                                    OrderStatusEnum status,
                                    Long subServiceId,
                                    Long serviceId);
}
