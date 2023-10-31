package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.CustomerRequestDto;
import com.example.serviceprovider.dto.CustomerResponseDto;
import com.example.serviceprovider.exception.DuplicatedInstanceException;
import com.example.serviceprovider.model.Customer;
import com.example.serviceprovider.service.CustomerService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDto> register(@RequestBody @Valid CustomerRequestDto customerRequestDto) {
        Customer customer = modelMapper.map(customerRequestDto, Customer.class);
        Customer addedCustomer;
        try {
            addedCustomer = customerService.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedInstanceException(String.format("%s is duplicated.", customer.getEmail()));
        }
        CustomerResponseDto customerResponseDto = modelMapper.map(addedCustomer, CustomerResponseDto.class);
        return new ResponseEntity<>(customerResponseDto, HttpStatus.CREATED);
    }
}
