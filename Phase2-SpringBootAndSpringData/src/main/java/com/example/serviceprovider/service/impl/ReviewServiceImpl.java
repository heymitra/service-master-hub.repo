package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.exception.InvalidInputException;
import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.model.Offer;
import com.example.serviceprovider.model.Order;
import com.example.serviceprovider.model.Review;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import com.example.serviceprovider.repository.ReviewRepository;
import com.example.serviceprovider.service.ExpertService;
import com.example.serviceprovider.service.OrderService;
import com.example.serviceprovider.service.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final OrderService orderService;
    private final ExpertService expertService;

    public ReviewServiceImpl(ReviewRepository repository, OrderService orderService, ExpertService expertService) {
        this.repository = repository;
        this.orderService = orderService;
        this.expertService = expertService;
    }

    @Override
    public Review add(Review review, Long orderId) {

        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new InvalidInputException("Order with ID " + orderId + " not found."));

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
