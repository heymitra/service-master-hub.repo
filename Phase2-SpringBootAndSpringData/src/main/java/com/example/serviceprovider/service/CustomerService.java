package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Customer;

import java.util.Optional;

public interface CustomerService {
    Customer save(Customer customer);
    Customer update(Customer customer);
    Optional<Customer> findById(Long id);
}
