package com.example.serviceprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponseDTO {
    Long offerId;
    Long orderId;
    Long expertId;
}
