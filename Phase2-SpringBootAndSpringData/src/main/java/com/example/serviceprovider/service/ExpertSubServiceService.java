package com.example.serviceprovider.service;

import com.example.serviceprovider.model.ExpertSubService;

public interface ExpertSubServiceService {
    ExpertSubService save(Long expertId, Long subServiceId);
    void remove(Long expertId, Long subServiceId);
}
