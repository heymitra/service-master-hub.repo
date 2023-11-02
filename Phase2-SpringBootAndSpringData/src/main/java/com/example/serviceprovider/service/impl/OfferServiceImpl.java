package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.exception.ItemNotFoundException;
import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.model.Offer;
import com.example.serviceprovider.model.Order;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import com.example.serviceprovider.repository.OfferRepository;
import com.example.serviceprovider.service.ExpertService;
import com.example.serviceprovider.service.OfferService;
import com.example.serviceprovider.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository repository;
    private final ExpertService expertService;
    private final OrderService orderService;

    public OfferServiceImpl(OfferRepository repository, ExpertService expertService, OrderService orderService) {
        this.repository = repository;
        this.expertService = expertService;
        this.orderService = orderService;
    }

    public Offer save(Offer offer, Long expertId, Long orderId) {
        Expert expert = expertService.findById(expertId).orElse(null);
        Order order = orderService.findById(orderId).orElse(null);

        if (expert == null || order == null) {
            throw new ItemNotFoundException("Invalid expertId or orderId");
        }

        boolean isSubServiceRelated = expert.getSubServices().stream()
                .anyMatch(subService -> subService.getId().equals(order.getSubService().getId()));

        if (!isSubServiceRelated) {
            throw new IllegalArgumentException("The expert is not related to the subService of this order.");
        }

        offer.setOrder(order);
        offer.setExpert(expert);
        offer.setOfferDateTime(LocalDateTime.now());

        if (!Arrays.asList(OrderStatusEnum.WAITING_FOR_OFFERS, OrderStatusEnum.WAITING_FOR_EXPERT_SELECTION)
                .contains(order.getStatus())) {
            throw new IllegalArgumentException("The order is not in a valid status for offers.");
        }
        if (offer.getOfferedPrice() < order.getSubService().getBasePrice()) {
            throw new IllegalArgumentException("Proposed price cannot be lower than the base price of the sub-service.");
        }
        if (offer.getOfferedStartTime().isBefore(order.getCompletionDateTime())) {
            throw new IllegalArgumentException("Start time cannot be before the customer's offered time.");
        }
        return repository.save(offer);
    }

    @Override
    public Offer update(Offer offer) {
        return repository.save(offer);
    }

    @Override
    public List<Offer> viewOffers(Long orderId, String sortBy) {
        List<Offer> orderOffers = repository.findOffersByOrder(orderId);
        if (orderOffers.isEmpty()) {
            return Collections.emptyList();
        }

        if ("price".equalsIgnoreCase(sortBy)) {
            orderOffers.sort(Comparator.comparing(Offer::getOfferedPrice));
        } else if ("score".equalsIgnoreCase(sortBy)) {
            orderOffers.sort(Comparator.comparing(offer -> offer.getExpert().getScore(), Comparator.reverseOrder()));
        } else {
            throw new IllegalArgumentException("Invalid sorting criteria. Please use 'price' or 'score'.");
        }

        return orderOffers;
    }

    @Override
    @Transactional
    public void selectOffer(Long orderId, Long selectedOfferId) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new ItemNotFoundException("Order with ID " + orderId + " not found"));

        Offer selectedOffer = repository.findById(selectedOfferId)
                .orElseThrow(() -> new ItemNotFoundException("Offer with ID " + selectedOfferId + " not found"));

        if (!selectedOffer.getOrder().equals(order)) {
            throw new IllegalArgumentException("The selected offer does not belong to the specified order.");
        }

        order.setStatus(OrderStatusEnum.WAITING_FOR_EXPERT_TO_COME);
        order.setSelectedOffer(selectedOffer);

        orderService.update(order);
        repository.save(selectedOffer);
    }

}
