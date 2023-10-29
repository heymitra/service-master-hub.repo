package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.Customer;
import com.example.serviceprovider.repository.CustomerRepository;
import com.example.serviceprovider.service.CustomerService;
import com.example.serviceprovider.validation.LogInfoValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    LogInfoValidator logInfoValidator;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
        logInfoValidator = new LogInfoValidator();
    }

    @Override
    public Customer save(Customer customer) {
        customer.setRegistrationDateTime(LocalDateTime.now());
        return repository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        return repository.save(customer);
    }
}
