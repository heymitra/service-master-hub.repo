package com.example.serviceprovider.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubServiceRequestDto {
    @NotBlank(message = "Sub-Service name is required.")
    private String name;

    @NotNull(message = "Base price cannot be null.")
    @Positive(message = "Price cannot be negative.")
    private double basePrice;

    @NotBlank(message = "Description cannot be blank.")
    private String description;
}
