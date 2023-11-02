package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.OrderRequestDto;
import com.example.serviceprovider.dto.OrderResponseDTO;
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
    public ResponseEntity<OrderResponseDTO> add(@RequestBody @Valid OrderRequestDto orderRequestDto,
                                                @RequestParam Long subServiceId,
                                                @RequestParam Long customerId) {
        Order order = modelMapper.map(orderRequestDto, Order.class);
        Order savedOrder = orderService.save(order, subServiceId, customerId);
        OrderMapper orderMapper = new OrderMapper();
        OrderResponseDTO orderResponseDTO = orderMapper.modelToDto(savedOrder);

        return new ResponseEntity<>(orderResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/start")
    public void startOrder(@RequestParam Long orderId) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with " + orderId + " order id not found"));

        if (order.getStatus() != OrderStatusEnum.WAITING_FOR_EXPERT_TO_COME) {
            throw new IllegalArgumentException("The order with this status cannot start.");
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
        orderService.update(order);
    }

    @PutMapping("/complete")
    public void completeOrder(@RequestParam Long orderId) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with " + orderId + " order id not found"));
        if (order.getStatus() != OrderStatusEnum.STARTED) {
            throw new IllegalArgumentException("The order is not in the 'Started' status.");
        }

        order.setStatus(OrderStatusEnum.COMPLETED);

        orderService.completeOrder(order);
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
    public void payWithCredit(@RequestParam Long orderId) {

        Order toBePayedOrder = getToBePayedOrder(orderId);
        orderService.makePayment(toBePayedOrder);

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
    public OrderResponseDTO modelToDto(Order order) {
        return new OrderResponseDTO(order.getId(),
                order.getSubService().getId(),
                order.getCustomer().getId());
    }
}