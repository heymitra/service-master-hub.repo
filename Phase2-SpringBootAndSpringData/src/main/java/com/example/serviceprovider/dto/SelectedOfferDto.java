package com.example.serviceprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectedOfferDto {
    private LocalDateTime offerDateTime;
    private double offeredPrice;
    private LocalDateTime offeredStartTime;
    private int offeredDurationInHours;
    private ExpertResponseDto expert;
}
