package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Expert;

import java.util.Optional;

public interface ExpertService {
    Optional<Expert> findById(Long id);
    Expert save(Expert expert);
    Expert update(Expert expert);
    Expert approveExpert(Long id);
    Optional<Expert> findByEmail(String email);
    void activate(Expert expert);
}
