package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.model.Offer;
import com.example.serviceprovider.model.Order;
import com.example.serviceprovider.model.SubService;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import com.example.serviceprovider.repository.OrderRepository;
import com.example.serviceprovider.service.ExpertService;
import com.example.serviceprovider.service.OfferService;
import com.example.serviceprovider.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    private OfferService offerService;
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order saveOrder(Order order) {
        if (order.getProposedPrice() < order.getSubService().getBasicPrice()) {
            throw new IllegalArgumentException("Proposed price cannot be less than the base price of the sub-service.");
        }

        if (order.getCompletionDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Completion date and time cannot be in the past.");
        }

        return repository.save(order);
    }

    @Override
    public Order save(Order order) {
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

    @Transactional
    public void selectOfferAndChangeOrderStatus(Order order, Offer selectedOffer) {
        if (!selectedOffer.getOrder().equals(order)) {
            throw new IllegalArgumentException("The selected offer does not belong to the specified order.");
        }

        order.setStatus(OrderStatusEnum.WAITING_FOR_EXPERT_TO_COME);

        repository.save(order);
        offerService.save(selectedOffer);
    }

    @Transactional
    public void startOrder(Order order) {
        if (order.getStatus() != OrderStatusEnum.WAITING_FOR_EXPERT_TO_COME) {
            throw new IllegalArgumentException("The order is not in the 'waiting for the expert to come' status.");
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
        repository.save(order);
    }

    @Transactional
    public void completeOrder(Order order) {
        if (order.getStatus() != OrderStatusEnum.STARTED) {
            throw new IllegalArgumentException("The order is not in the 'Started' status.");
        }

        order.setStatus(OrderStatusEnum.COMPLETED);

        repository.save(order);
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
