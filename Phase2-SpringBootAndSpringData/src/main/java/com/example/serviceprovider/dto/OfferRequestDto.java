package com.example.serviceprovider.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfferRequestDto {
    @Positive(message = "Price cannot be negative.")
    private double offeredPrice;

    @FutureOrPresent(message = "Start time of work cannot be in the past.")
    private LocalDateTime offeredStartTime;

    @Positive(message = "Duration must be a positive number, representing hours.")
    private int offeredDurationInHours;
}
