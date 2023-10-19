package com.example.serviceprovider.repository;

import com.example.serviceprovider.model.Order;
import com.example.serviceprovider.model.SubService;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.status IN :statuses AND o.subService IN :subServices")
    List<Order> findOrdersByStatusAndSubServiceIn(@Param("statuses") List<OrderStatusEnum> statuses, @Param("subServices") List<SubService> subServices);
}
