package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.Service;
import com.example.serviceprovider.repository.ServiceRepository;
import com.example.serviceprovider.service.ServiceService;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository repository;

    public ServiceServiceImpl(ServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Service saveOrUpdate(Service service) {
        Optional<Service> existingService = findByServiceName(service.getServiceName());
        if (existingService.isPresent()) {
            Service updatedService = existingService.get();
            return repository.save(updatedService);
        } else {
            return repository.save(service);
        }
    }


    @Override
    public Optional<Service> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Service> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Service service) {
        repository.deleteById(service.getId());
    }

    @Override
    public Optional<Service> findByServiceName(String serviceName) {
        return repository.findByServiceName(serviceName);
    }
}
