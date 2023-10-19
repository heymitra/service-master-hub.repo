package com.example.serviceprovider.repository;

import com.example.serviceprovider.model.SubService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubServiceRepository extends JpaRepository<SubService, Long> {
    Optional<SubService> findBySubServiceName(String subServiceName);
    List<SubService> findSubServicesByServiceId(Long serviceId);
}
