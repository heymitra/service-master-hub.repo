package service;

import base.service.BaseService;
import entity.Order;
import entity.User;
import entity.enumeration.OrderStatusEnum;

import java.time.LocalDateTime;

public interface OrderService extends BaseService<Order, Long> {
    Order save(String email, Long subServiceId, double proposedPrice, String workDescription, String address, LocalDateTime completionDateTime, OrderStatusEnum status);
}
