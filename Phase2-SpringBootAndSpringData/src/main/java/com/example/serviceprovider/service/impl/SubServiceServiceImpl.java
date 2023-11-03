package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.exception.InvalidInputException;
import com.example.serviceprovider.model.Service;
import com.example.serviceprovider.model.SubService;
import com.example.serviceprovider.repository.SubServiceRepository;
import com.example.serviceprovider.service.ServiceService;
import com.example.serviceprovider.service.SubServiceService;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class SubServiceServiceImpl implements SubServiceService {
    private final SubServiceRepository repository;
    private final ServiceService serviceService;

    public SubServiceServiceImpl(SubServiceRepository repository, ServiceService serviceService) {
        this.repository = repository;
        this.serviceService = serviceService;
    }

    @Override
    public SubService save(SubService subService, Long serviceId) {
        Service service = serviceService.findById(serviceId)
                .orElseThrow(() -> new InvalidInputException("Service with ID " + serviceId + " not found."));

        subService.setService(service);
        return repository.save(subService);
    }

    @Override
    public SubService update(SubService subService) {
        return repository.save(subService);
    }

    @Override
    public Optional<SubService> findBySubServiceName(String subServiceName) {
        return repository.findBySubServiceName(subServiceName);
    }

    @Override
    public Optional<SubService> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<SubService> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(SubService subService) {
        repository.deleteById(subService.getId());
    }

    @Override
    public List<SubService> findSubServicesByServiceId(Long serviceId) {
        return repository.findSubServicesByServiceId(serviceId);
    }
}
