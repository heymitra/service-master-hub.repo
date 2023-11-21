package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.*;
import com.example.serviceprovider.model.Order;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import com.example.serviceprovider.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    //1

    //2
    @GetMapping("/user-report")
    public List<OrderReportDto> getOrdersByUser(@RequestParam Long userId) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        OrderReportDto orderReportDTO = new OrderReportDto();
        return orderReportDTO.getOrderDetailsDTOS(orders, modelMapper);
    }

    //3
    @GetMapping("/order-report")
    public List<OrderReportDto> getOrdersByCriteria(
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime,
            @RequestParam(required = false) OrderStatusEnum status,
            @RequestParam(required = false) Long subServiceId,
            @RequestParam(required = false) Long serviceId) {

        List<Order> orders = orderService.getOrdersByCriteria(startTime, endTime, status, subServiceId, serviceId);
        OrderReportDto orderReportDTO = new OrderReportDto();
        return orderReportDTO.getOrderDetailsDTOS(orders, modelMapper);
    }
}