package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.exception.InsufficientCreditException;
import com.example.serviceprovider.exception.ItemNotFoundException;
import com.example.serviceprovider.model.*;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import com.example.serviceprovider.repository.OrderRepository;
import com.example.serviceprovider.service.CustomerService;
import com.example.serviceprovider.service.OrderService;
import com.example.serviceprovider.service.SubServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final SubServiceService subServiceService;
    private final CustomerService customerService;

    public OrderServiceImpl(OrderRepository repository, SubServiceService subServiceService, CustomerService customerService) {
        this.repository = repository;
        this.subServiceService = subServiceService;
        this.customerService = customerService;
    }

    @Override
    public Order save(Order order, Long subServiceId, Long customerId) {
        Customer customer = customerService.findById(customerId)
                .orElseThrow(() -> new ItemNotFoundException("Customer with ID " + customerId + " not found"));

        SubService subService = subServiceService.findById(subServiceId)
                .orElseThrow(() -> new ItemNotFoundException("Sub-service with ID " + subServiceId + " not found"));

        if (order.getProposedPrice() < subService.getBasePrice()) {
            throw new IllegalArgumentException("Proposed price cannot be less than the base price of the sub-service.");
        }

        if (order.getCompletionDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Completion date and time cannot be in the past.");
        }

        order.setStatus(OrderStatusEnum.WAITING_FOR_OFFERS);
        order.setSubService(subService);
        order.setCustomer(customer);
        return repository.save(order);
    }

    @Override
    public Order update(Order order) {
        return repository.save(order);
    }

    @Override
    public List<Order> getOrdersForExpert(Expert expert) {
        List<SubService> expertSubServices = expert.getSubServices();
        return repository.findOrdersByStatusAndSubServiceIn(
                Arrays.asList(OrderStatusEnum.WAITING_FOR_OFFERS, OrderStatusEnum.WAITING_FOR_EXPERT_SELECTION),
                expertSubServices
        );
    }

    @Override
    @Transactional
    public void makePayment(Order order) {
        Customer customer = order.getCustomer();

        double orderPrice = order.getSelectedOffer().getOfferedPrice();
        double customerCredit = customer.getCredit();

        if (customerCredit >= orderPrice) {
            customer.setCredit(customerCredit - orderPrice);
            customerService.update(customer);

            order.setStatus(OrderStatusEnum.PAID);
            repository.save(order);

        } else {
            throw new InsufficientCreditException("Insufficient credit to make the payment");
        }
    }


    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long orderId) {
        repository.deleteById(orderId);
    }
}
