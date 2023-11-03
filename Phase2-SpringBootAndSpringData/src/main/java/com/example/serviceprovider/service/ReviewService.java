package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Review;

public interface ReviewService {
    Review add(Review review, Long orderId);
}
