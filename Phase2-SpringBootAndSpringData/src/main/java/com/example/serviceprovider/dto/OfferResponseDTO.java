package com.example.serviceprovider.dto;

import com.example.serviceprovider.model.Offer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponseDto {
    Long offerId;
    Long orderId;
    Long expertId;

    public OfferResponseDto modelToDto(Offer offer) {
        return new OfferResponseDto(offer.getId(),
                offer.getOrder().getId(),
                offer.getExpert().getId());
    }
}
