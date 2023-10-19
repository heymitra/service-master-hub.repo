package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceService {
    Service saveOrUpdate(Service service);
    Optional<Service> findById(Long id);
    List<Service> findAll();
    void deleteById(Service service);
    Optional<Service> findByServiceName(String serviceName);
}
