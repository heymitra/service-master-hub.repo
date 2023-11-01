package com.example.serviceprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    Long orderId;
    Long subServiceId;
    Long customerId;
}
