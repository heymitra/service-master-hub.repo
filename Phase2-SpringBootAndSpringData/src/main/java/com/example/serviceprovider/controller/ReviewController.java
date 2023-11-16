package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.ReviewRequestDto;
import com.example.serviceprovider.dto.ReviewResponseDto;
import com.example.serviceprovider.model.Review;
import com.example.serviceprovider.service.ReviewService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService service;
    private final ModelMapper modelMapper;

    public ReviewController(ReviewService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/add")
    public ResponseEntity<ReviewResponseDto> add(@RequestBody @Valid ReviewRequestDto reviewRequestDto,
                                                 @RequestParam Long orderId) {
        Review review = modelMapper.map(reviewRequestDto, Review.class);
        Review addedReview = service.add(review, orderId);
        ReviewResponseDto reviewResponseDto = modelMapper.map(addedReview, ReviewResponseDto.class);
        return new ResponseEntity<>(reviewResponseDto, HttpStatus.CREATED);
    }
}
