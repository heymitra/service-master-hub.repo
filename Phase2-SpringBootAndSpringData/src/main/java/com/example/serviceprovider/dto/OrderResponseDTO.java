package com.example.serviceprovider.dto;

import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Long id;
    private double proposedPrice;
    private String workDescription;
    private LocalDateTime completionDateTime;
    private String address;
    private OrderStatusEnum status;
}
