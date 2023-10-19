package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.service.dto.ExpertDTO;

import java.util.List;
import java.util.Optional;

public interface ExpertService {
    List<ExpertDTO> findAllExperts();
    Optional<Expert> findById(Long id);
    Expert saveOrUpdate(Expert expert);
    Expert approveExpert(Long id);
}
