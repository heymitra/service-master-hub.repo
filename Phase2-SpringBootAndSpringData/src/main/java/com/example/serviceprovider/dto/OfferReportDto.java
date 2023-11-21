package com.example.serviceprovider.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OfferReportDto {
    private LocalDateTime offerDateTime;
    private double offeredPrice;
    private LocalDateTime offeredStartTime;
    private int offeredDurationInHours;
    private boolean isSelected;
    private ExpertResponseDto expert;
    private OrderResponseDto order;
}
