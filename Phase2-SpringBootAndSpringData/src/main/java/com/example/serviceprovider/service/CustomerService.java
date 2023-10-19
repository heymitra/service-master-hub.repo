package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Customer;

public interface CustomerService {
    Customer saveOrUpdate(Customer customer);
}
