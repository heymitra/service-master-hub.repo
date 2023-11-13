package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.Customer;
import com.example.serviceprovider.model.enumeration.Role;
import com.example.serviceprovider.repository.CustomerRepository;
import com.example.serviceprovider.service.CustomerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository repository,
                               BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer save(Customer customer) {
        customer.setRegistrationDateTime(LocalDateTime.now());
        customer.setCredit(0);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        return repository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }
}
