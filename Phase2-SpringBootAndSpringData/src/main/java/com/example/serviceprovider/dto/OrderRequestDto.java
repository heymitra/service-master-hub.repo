package com.example.serviceprovider.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    @Positive(message = "Price cannot be negative.")
    private double proposedPrice;

    @NotBlank(message = "Description is required.")
    private String workDescription;

    @FutureOrPresent(message = "Completion date and time cannot be in the past.")
    private LocalDateTime completionDateTime;

    @NotBlank(message = "Address is required.")
    private String address;
}
