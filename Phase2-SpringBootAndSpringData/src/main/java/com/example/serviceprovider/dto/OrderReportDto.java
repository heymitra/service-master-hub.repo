package com.example.serviceprovider.dto;

import com.example.serviceprovider.model.Order;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderReportDto {
    private Long orderId;
    private SubServiceResponseDto subService;
    private double proposedPrice;
    private String workDescription;
    private LocalDateTime expectedCompletionTime;
    private LocalDateTime applyTime;
    private LocalDateTime realCompletionTime;
    private String address;
    private OrderStatusEnum status;
    private CustomerResponseDto customer;
    private SelectedOfferDto selectedOffer;

    public List<OrderReportDto> getOrderDetailsDTOS(List<Order> orders, ModelMapper modelMapper) {
        return orders.stream()
                .map(order -> {
                    OrderReportDto dto = modelMapper.map(order, OrderReportDto.class);
                    dto.setSubService(modelMapper.map(order.getSubService(), SubServiceResponseDto.class));
                    dto.setCustomer(modelMapper.map(order.getCustomer(), CustomerResponseDto.class));

                    SelectedOfferDto selectedOfferDto = null;

                    if (order.getSelectedOffer() != null) {
                        // Map SelectedOfferDto
                        selectedOfferDto = modelMapper.map(order.getSelectedOffer(), SelectedOfferDto.class);

                        // Map ExpertResponseDto within SelectedOfferDto
                        selectedOfferDto.setExpert(modelMapper.map(order.getSelectedOffer().getExpert(), ExpertResponseDto.class));
                    }

                    dto.setSelectedOffer(selectedOfferDto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}


