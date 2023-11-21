package com.example.serviceprovider.controller;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.gimpy.DropShadowGimpyRenderer;
import com.example.serviceprovider.dto.*;
import com.example.serviceprovider.exception.InvalidInputException;
import com.example.serviceprovider.exception.ItemNotFoundException;
import com.example.serviceprovider.model.Order;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import com.example.serviceprovider.service.OrderService;
import com.example.serviceprovider.utility.ImageUtility;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final ImageUtility imageUtility;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping("/payment-form")
    public String showPaymentForm() {
        return "payment";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/add")
    public ResponseEntity<OrderByIdsDto> add(@RequestBody @Valid OrderRequestDto orderRequestDto,
                                             @RequestParam Long subServiceId,
                                             @RequestParam Long customerId) {
        Order order = modelMapper.map(orderRequestDto, Order.class);
        Order savedOrder = orderService.save(order, subServiceId, customerId);
        OrderByIdsDto orderMapper = new OrderByIdsDto();
        OrderByIdsDto orderByIdsDto = orderMapper.modelToDto(savedOrder);

        return new ResponseEntity<>(orderByIdsDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/start")
    public ResponseEntity<OrderResponseDto> startOrder(@RequestParam Long orderId) {
        Order updatedOrder = orderService.start(orderId);
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto = orderResponseDto.fromOrder(updatedOrder, modelMapper);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/complete")
    public ResponseEntity<OrderResponseDto> completeOrder(@RequestParam Long orderId) {
        Order completedOrder = orderService.completeOrder(orderId);
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto = orderResponseDto.fromOrder(completedOrder, modelMapper);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/credit-payment")
    public ResponseEntity<OrderResponseDto> payWithCredit(@RequestParam Long orderId) {
        Order toBePayedOrder = getToBePayedOrder(orderId);
        Order order = orderService.makePayment(toBePayedOrder);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EXPERT')")
    @GetMapping("/get-orders")
    public ResponseEntity<List<OrderResponseDto>> getAvailableOrdersByExpert() {
        List<Order> orders = orderService.getAvailableOrdersByExpert();
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

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/online-payment")
    public ResponseEntity<String> makePayment(
            @RequestParam String cardNumber,
            @RequestParam String cvv2,
            @RequestParam String password,
            @RequestParam Long orderId,
            @RequestParam String captcha,
            HttpSession session
    ) {
        String storedCaptcha = (String) session.getAttribute("captcha");

        if (!captcha.equals(storedCaptcha)) {
            return new ResponseEntity<>("Wrong captcha. ", HttpStatus.BAD_REQUEST);
        }

        if (!isValidCardInfo(cardNumber, cvv2, password)) {
            return new ResponseEntity<>("Invalid card information. Payment failed.", HttpStatus.BAD_REQUEST);
        }

        Order toBePayedOrder = getToBePayedOrder(orderId);

        orderService.makePayment(toBePayedOrder);

        System.out.println(cardNumber + cvv2 + password);
        return ResponseEntity.ok("Payment successful");
    }

    @GetMapping("/captcha-image")
    public ResponseEntity<byte[]> generateCaptchaImage(HttpSession session) {
        Captcha captcha = new Captcha.Builder(200, 50)
                .addText()
                .addNoise()
                .addBackground()
                .addBorder()
                .gimp(new DropShadowGimpyRenderer())
                .build();

        session.setAttribute("captcha", captcha.getAnswer());

        BufferedImage image = captcha.getImage();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        byte[] imageBytes = imageUtility.convertImageToByteArray(image);

        return new ResponseEntity<>(imageBytes, headers, 200);
    }

    //1.
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my-orders-criteria")
    public Page<OrderResponseDto> searchAndFilterOrders(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Order> orders = orderService.searchAndFilterOrders(status, PageRequest.of(page, size));
        return orders.map(order -> modelMapper.map(order, OrderResponseDto.class));
    }

    //2.
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my-orders")
    public List<OrderReportDto> getOrdersByCustomerAndStatus(@RequestParam(required = false) String status) {
        OrderStatusEnum orderStatus = null;
        if (status != null) {
            try {
                orderStatus = OrderStatusEnum.valueOf(status); // Convert status string to OrderStatusEnum
            } catch (IllegalArgumentException e) {
                logger.error("Error: Invalid OrderStatusEnum value provided: {}", status);
            }
        }
        List<Order> orders = orderService.getOrdersByCustomerAndStatus(orderStatus);
        OrderReportDto orderReportDTO = new OrderReportDto();
        return orderReportDTO.getOrderDetailsDTOS(orders, modelMapper);
    }
}