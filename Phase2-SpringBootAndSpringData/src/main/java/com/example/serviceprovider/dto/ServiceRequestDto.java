package com.example.serviceprovider.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestDto {
    @NotBlank(message = "Service name is required.")
    private String name;
}
