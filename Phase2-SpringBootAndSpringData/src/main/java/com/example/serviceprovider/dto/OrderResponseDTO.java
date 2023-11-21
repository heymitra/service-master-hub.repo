package com.example.serviceprovider.dto;

import com.example.serviceprovider.model.Order;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private SubServiceResponseDto subService;
    private double proposedPrice;
    private String workDescription;
    private LocalDateTime expectedCompletionTime;
    private LocalDateTime applyTime;
    private LocalDateTime realCompletionTime;
    private String address;
    private OrderStatusEnum status;

    public OrderResponseDto fromOrder(Order order, ModelMapper modelMapper) {
        return modelMapper.map(order, OrderResponseDto.class);
    }
}
