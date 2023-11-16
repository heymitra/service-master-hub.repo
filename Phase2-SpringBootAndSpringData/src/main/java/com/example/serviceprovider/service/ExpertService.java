package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.service.dto.ExpertDTO;

import java.util.List;
import java.util.Optional;

public interface ExpertService {
    List<ExpertDTO> findAllExperts();
    Optional<Expert> findById(Long id);
    Expert save(Expert expert);
    Expert update(Expert expert);
    Expert approveExpert(Long id);
    int viewRate (Long expertId);
    Optional<Expert> findByEmail(String email);
    void activate(Expert expert);
}
