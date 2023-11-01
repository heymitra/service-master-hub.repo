package com.example.serviceprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SubServiceResponseDto {
    private Long id;
    private String subServiceName;
    private double basePrice;
    private String description;
}
