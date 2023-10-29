package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Customer;

public interface CustomerService {
    Customer save(Customer customer);
    Customer update(Customer customer);
}
