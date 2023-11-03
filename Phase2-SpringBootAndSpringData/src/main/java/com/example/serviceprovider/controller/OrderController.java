package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.OrderRequestDto;
import com.example.serviceprovider.dto.OrderByIdsDto;
import com.example.serviceprovider.dto.OrderResponseDto;
import com.example.serviceprovider.exception.InvalidInputException;
import com.example.serviceprovider.exception.ItemNotFoundException;
import com.example.serviceprovider.model.Offer;
import com.example.serviceprovider.model.Order;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import com.example.serviceprovider.service.OrderService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/payment-form")
    public String showPaymentForm() {
        return "payment";
    }

    @PostMapping("/add")
    public ResponseEntity<OrderByIdsDto> add(@RequestBody @Valid OrderRequestDto orderRequestDto,
                                             @RequestParam Long subServiceId,
                                             @RequestParam Long customerId) {
        Order order = modelMapper.map(orderRequestDto, Order.class);
        Order savedOrder = orderService.save(order, subServiceId, customerId);
        OrderMapper orderMapper = new OrderMapper();
        OrderByIdsDto orderByIdsDto = orderMapper.modelToDto(savedOrder);

        return new ResponseEntity<>(orderByIdsDto, HttpStatus.CREATED);
    }

    @PutMapping("/start")
    public ResponseEntity<OrderResponseDto> startOrder(@RequestParam Long orderId) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with " + orderId + " order id not found"));

        if (order.getStatus() != OrderStatusEnum.WAITING_FOR_EXPERT_TO_COME) {
            throw new IllegalArgumentException("The order with this status cannot be started.");
        }

        Offer selectedOffer = order.getSelectedOffer();
        if (selectedOffer == null) {
            throw new IllegalArgumentException("No offer is selected for this order.");
        }

        LocalDateTime offeredStartTime = selectedOffer.getOfferedStartTime();

        if (LocalDateTime.now().isBefore(offeredStartTime)) {
            throw new IllegalArgumentException("The order cannot be started before the offered start time.");
        }

        order.setStatus(OrderStatusEnum.STARTED);
        Order updatedOrder = orderService.update(order);
        OrderResponseDto orderResponseDto = modelMapper.map(updatedOrder, OrderResponseDto.class);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    @PutMapping("/complete")
    public ResponseEntity<OrderResponseDto> completeOrder(@RequestParam Long orderId) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with " + orderId + " order id not found."));

        OrderStatusEnum status = order.getStatus();
        if (status == OrderStatusEnum.COMPLETED || status == OrderStatusEnum.PAID) {
            throw new IllegalArgumentException("The order has been completed before.");
        } else if (status != OrderStatusEnum.STARTED) {
            throw new IllegalArgumentException("The order has not been started yet.");
        }

        order.setStatus(OrderStatusEnum.COMPLETED);

        Order completedOrder = orderService.completeOrder(order);
        OrderResponseDto orderResponseDto = modelMapper.map(orderService, OrderResponseDto.class);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    @PostMapping("/online-payment")
    public ResponseEntity<String> makePayment(
            @RequestParam String cardNumber,
            @RequestParam String cvv2,
            @RequestParam String secondPassword,
            @RequestParam Long orderId
    ) {
        if (!isValidCardInfo(cardNumber, cvv2, secondPassword)) {
            return new ResponseEntity<>("Invalid card information. Payment failed.", HttpStatus.BAD_REQUEST);
        }

        Order toBePayedOrder = getToBePayedOrder(orderId);

        orderService.makePayment(toBePayedOrder);

        System.out.println(cardNumber + cvv2 + secondPassword);
        return ResponseEntity.ok("Payment successful");
    }

    @GetMapping("/credit-payment")
    public ResponseEntity<OrderResponseDto> payWithCredit(@RequestParam Long orderId) {
        Order toBePayedOrder = getToBePayedOrder(orderId);
        Order order = orderService.makePayment(toBePayedOrder);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get-orders")
    public ResponseEntity<List<OrderResponseDto>> getAvailableOrdersByExpert(@RequestParam Long expertId) {
        List<Order> orders = orderService.getAvailableOrdersByExpert(expertId);
        List<OrderResponseDto> responseDtos = new ArrayList<>();
        for (Order order : orders) {
            responseDtos.add(modelMapper.map(order, OrderResponseDto.class));
        }
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    private Order getToBePayedOrder(Long orderId) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new ItemNotFoundException("Order with ID " + orderId + " not found"));

        OrderStatusEnum orderStatus = order.getStatus();
        if (orderStatus != OrderStatusEnum.COMPLETED) {
            throw new InvalidInputException(String.format("Order in %s status does not require payment.", orderStatus));
        } else
            return order;
    }

    private boolean isValidCardInfo(String cardNumber, String cvv2, String secondPassword) {
        return cardNumber != null && cardNumber.matches("\\d{16}")
                && cvv2 != null && cvv2.matches("\\d{3,4}")
                && secondPassword != null && secondPassword.matches("\\d{8,12}");
    }
}

class OrderMapper {
    public OrderByIdsDto modelToDto(Order order) {
        return new OrderByIdsDto(order.getId(),
                order.getSubService().getId(),
                order.getCustomer().getId());
    }
}