package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.exception.InsufficientCreditException;
import com.example.serviceprovider.exception.InvalidInputException;
import com.example.serviceprovider.exception.ItemNotFoundException;
import com.example.serviceprovider.model.*;
import com.example.serviceprovider.model.enumeration.ExpertStatusEnum;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import com.example.serviceprovider.repository.OrderRepository;
import com.example.serviceprovider.service.CustomerService;
import com.example.serviceprovider.service.ExpertService;
import com.example.serviceprovider.service.OrderService;
import com.example.serviceprovider.service.SubServiceService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final SubServiceService subServiceService;
    private final CustomerService customerService;
    private final ExpertService expertService;

    public OrderServiceImpl(OrderRepository repository,
                            SubServiceService subServiceService,
                            CustomerService customerService,
                            ExpertService expertService) {
        this.repository = repository;
        this.subServiceService = subServiceService;
        this.customerService = customerService;
        this.expertService = expertService;
    }

    @Override
    public Order save(Order order, Long subServiceId, Long customerId) {
        Customer customer = customerService.findById(customerId)
                .orElseThrow(() -> new ItemNotFoundException("Customer with ID " + customerId + " not found."));

        SubService subService = subServiceService.findById(subServiceId)
                .orElseThrow(() -> new ItemNotFoundException("Sub-service with ID " + subServiceId + " not found."));

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
    public List<Order> getAvailableOrdersByExpert() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Expert expert = expertService.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Expert not found."));
        List<SubService> expertSubServices = expert.getSubServices();
        return repository.findOrdersByStatusAndSubServiceIn(
                Arrays.asList(OrderStatusEnum.WAITING_FOR_OFFERS, OrderStatusEnum.WAITING_FOR_EXPERT_SELECTION),
                expertSubServices
        );
    }

    @Override
    @Transactional
    public Order makePayment(Order order) {
        Customer customer = order.getCustomer();

        double orderPrice = order.getSelectedOffer().getOfferedPrice();
        double customerCredit = customer.getCredit();

        if (customerCredit >= orderPrice) {
            customer.setCredit(customerCredit - orderPrice);
            customerService.update(customer);

            order.setStatus(OrderStatusEnum.PAID);
            order.getSelectedOffer().getExpert().setCredit(orderPrice * 0.7);
            return repository.save(order);

        } else {
            throw new InsufficientCreditException("Insufficient credit to make the payment");
        }
    }

    @Transactional
    @Override
    public Order completeOrder(Order order) {
        Offer selectedOffer = order.getSelectedOffer();
        LocalDateTime offeredStartTime = selectedOffer.getOfferedStartTime();
        int offeredDurationInHours = selectedOffer.getOfferedDurationInHours();

        Expert expert = selectedOffer.getExpert();
        int expertScore = expert.getRate();

        LocalDateTime completionTime = LocalDateTime.now();

        if (completionTime.isAfter(offeredStartTime.plusHours(offeredDurationInHours))) {
            long delayInHours = Duration.between(offeredStartTime.plusHours(offeredDurationInHours), completionTime).toHours();

            expertScore -= Math.toIntExact(delayInHours);

            if (expertScore < 0) {
                expert.setExpertStatus(ExpertStatusEnum.INACTIVE);
            }
        }

        expert.setRate(expertScore);

        expertService.update(expert);
        return update(order);
    }

    @Override
    public Order start(Long orderId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Order order = findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with " + orderId + " order id not found"));

        if (!order.getCustomer().getEmail().equals(email))
            throw new InvalidInputException("You are not allowed to change the status of this order.");

        if (order.getStatus() != OrderStatusEnum.WAITING_FOR_EXPERT_TO_COME) {
            throw new IllegalArgumentException("The order with this status cannot be started.");
        }

        Offer selectedOffer = order.getSelectedOffer();
        if (selectedOffer == null) {
            throw new IllegalArgumentException("No offer is selected for this order.");
        }

        LocalDateTime offeredStartTime = selectedOffer.getOfferedStartTime();

        if (LocalDateTime.now().isBefore(offeredStartTime)) {
            throw new IllegalArgumentException("The order cannot be started before the offered start time.");
        }

        order.setStatus(OrderStatusEnum.STARTED);
        return update(order);
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
