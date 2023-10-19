package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.Offer;
import com.example.serviceprovider.model.Order;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import com.example.serviceprovider.repository.OfferRepository;
import com.example.serviceprovider.service.OfferService;
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

    public OfferServiceImpl(OfferRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Offer saveOffer(Offer offer) {
        if (!Arrays.asList(OrderStatusEnum.WAITING_FOR_OFFERS, OrderStatusEnum.WAITING_FOR_EXPERT_SELECTION).contains(offer.getOrder().getStatus())) {
            throw new IllegalArgumentException("The order is not in a valid status for offers.");
        }
        if (offer.getOfferedPrice() < offer.getOrder().getSubService().getBasicPrice()) {
            throw new IllegalArgumentException("Proposed price cannot be lower than the base price of the sub-service.");
        }
        if (offer.getOfferedStartTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start time cannot be in the past.");
        }
        return repository.save(offer);
    }

    @Override
    public Offer save(Offer offer) {
        return repository.save(offer);
    }

    @Override
    public List<Offer> viewCustomerOrderOffers(Order order, String sortBy) {
        List<Offer> orderOffers = repository.findOffersByOrder(order);
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
}
