package com.example.serviceprovider.service;

import com.example.serviceprovider.model.SubService;

import java.util.List;
import java.util.Optional;

public interface SubServiceService {
    SubService save(SubService subService, Long serviceId);
    SubService update(SubService subService);
    Optional<SubService> findById(Long id);
    List<SubService> findAll();
    void deleteById(SubService subService);
    Optional<SubService> findBySubServiceName(String subServiceName);
    List<SubService> findSubServicesByServiceId(Long serviceId);
}
