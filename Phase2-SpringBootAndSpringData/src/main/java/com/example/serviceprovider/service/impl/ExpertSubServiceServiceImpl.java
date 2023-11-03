package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.model.ExpertSubService;
import com.example.serviceprovider.model.SubService;
import com.example.serviceprovider.repository.ExpertSubServiceRepository;
import com.example.serviceprovider.service.ExpertService;
import com.example.serviceprovider.service.ExpertSubServiceService;
import com.example.serviceprovider.service.SubServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExpertSubServiceServiceImpl implements ExpertSubServiceService {
    private final ExpertSubServiceRepository repository;
    private final ExpertService expertService;
    private final SubServiceService subServiceService;

    public ExpertSubServiceServiceImpl(ExpertSubServiceRepository repository, ExpertService expertService, SubServiceService subServiceService) {
        this.repository = repository;
        this.expertService = expertService;
        this.subServiceService = subServiceService;
    }

    @Override
    public ExpertSubService save(Long expertId, Long subServiceId) {
        Expert expert = expertService.findById(expertId).orElse(null);
        SubService subService = subServiceService.findById(subServiceId).orElse(null);

        if (expert != null && subService != null) {
            ExpertSubService expertSubService = new ExpertSubService();
            expertSubService.setExpert(expert);
            expertSubService.setSubService(subService);

            return repository.save(expertSubService);
        } else {
            return null;
        }

    }

    @Override
    public void remove(Long expertId, Long subServiceId) {
        repository.deleteByExpertIdAndSubServiceId(expertId, subServiceId);
    }
}
