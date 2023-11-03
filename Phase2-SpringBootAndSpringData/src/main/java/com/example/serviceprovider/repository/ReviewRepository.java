package com.example.serviceprovider.repository;

import com.example.serviceprovider.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
