package com.example.serviceprovider.dto;

import com.example.serviceprovider.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderByIdsDto {
    Long orderId;
    Long subServiceId;
    Long customerId;

    public OrderByIdsDto modelToDto(Order order) {
        return new OrderByIdsDto(order.getId(),
                order.getSubService().getId(),
                order.getCustomer().getId());
    }
}
