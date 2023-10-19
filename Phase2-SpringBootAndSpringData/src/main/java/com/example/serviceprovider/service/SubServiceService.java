package com.example.serviceprovider.service;

import com.example.serviceprovider.exception.InvalidFormatException;
import com.example.serviceprovider.model.SubService;

import java.util.List;
import java.util.Optional;

public interface SubServiceService {
    SubService saveOrUpdate(SubService subService);
    Optional<SubService> findById(Long id);
    List<SubService> findAll();
    void deleteById(SubService subService);
    Optional<SubService> findBySubServiceName(String subServiceName);
    List<SubService> findSubServicesByServiceId(Long serviceId);
    void addExpertToSubService(Long expertId, Long subServiceId) throws InvalidFormatException;
}
