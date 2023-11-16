package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.Customer;
import com.example.serviceprovider.model.enumeration.Role;
import com.example.serviceprovider.repository.CustomerRepository;
import com.example.serviceprovider.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailSender mailSender;

    @Override
    public Customer save(Customer customer) {
        customer.setRegistrationDateTime(LocalDateTime.now());
        customer.setCredit(0);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRole(Role.ROLE_CUSTOMER);
        customer.setActivated(false);

        Customer savedCustomer = repository.save(customer);

        mailSender.sendActivationEmail(savedCustomer);

        return savedCustomer;
    }

    @Override
    public Customer update(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void activate(Customer customer) {
        customer.setActivated(true);
        update(customer);
    }
}
