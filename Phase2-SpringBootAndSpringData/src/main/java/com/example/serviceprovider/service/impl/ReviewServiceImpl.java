package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.exception.InvalidInputException;
import com.example.serviceprovider.model.*;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import com.example.serviceprovider.repository.ReviewRepository;
import com.example.serviceprovider.service.ExpertService;
import com.example.serviceprovider.service.OrderService;
import com.example.serviceprovider.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final OrderService orderService;
    private final ExpertService expertService;

    @Override
    public Review add(Review review, Long orderId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new InvalidInputException("Order with ID " + orderId + " not found."));

        if(!email.equals(order.getCustomer().getEmail()))
            throw new InvalidInputException("You are not allowed to add review on this order.");

        OrderStatusEnum status = order.getStatus();
        if (status != OrderStatusEnum.PAID && status != OrderStatusEnum.COMPLETED)
            throw new InvalidInputException(String.format("Order has not finished yes. Current Status: %s", status));


        Offer selectedOffer = order.getSelectedOffer();
        Expert expert = selectedOffer.getExpert();

        int totalRate = expert.getRate();
        int rate = review.getRate();

        int updatedTotalRate = totalRate + rate;
        expert.setRate(updatedTotalRate);

        expertService.update(expert);
        return repository.save(review);
    }
}
