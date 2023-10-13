package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Customer;
import entity.Order;
import entity.SubService;
import entity.User;
import entity.enumeration.OrderStatusEnum;
import repository.OrderRepository;
import service.OrderService;
import util.ApplicationContext;

import java.time.LocalDateTime;
import java.util.Optional;

public class OrderServiceImpl extends BaseServiceImpl<Order, Long, OrderRepository>
        implements OrderService {
    public OrderServiceImpl(OrderRepository repository) {
        super(repository);
    }

    @Override
    public Order save(String email, Long subServiceId, double proposedPrice, String workDescription, String address, LocalDateTime completionDateTime, OrderStatusEnum status) {
        Order order = new Order();
        Optional<SubService> subService = ApplicationContext.getSubServiceService().findById(subServiceId);
        order.setSubService(subService.get());
        User userByEmail = ApplicationContext.getUserService().findUserByEmail(email);
        order.setCustomer((Customer) userByEmail);
        order.setProposedPrice(proposedPrice);
        order.setWorkDescription(workDescription);
        order.setCompletionDateTime(completionDateTime);
        order.setAddress(address);
        order.setStatus(status);
        return super.save(order);
    }
}
