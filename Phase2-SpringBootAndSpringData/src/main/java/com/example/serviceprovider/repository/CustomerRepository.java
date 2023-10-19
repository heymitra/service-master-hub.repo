package com.example.serviceprovider.repository;

import com.example.serviceprovider.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
