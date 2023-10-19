package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.exception.SubServiceAlreadyExistsException;
import com.example.serviceprovider.exception.SubServiceNotFoundException;
import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.model.SubService;
import com.example.serviceprovider.repository.SubServiceRepository;
import com.example.serviceprovider.service.ExpertService;
import com.example.serviceprovider.service.SubServiceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubServiceServiceImpl implements SubServiceService {
    private final SubServiceRepository repository;
    public SubServiceServiceImpl(SubServiceRepository repository) {
        this.repository = repository;
    }

    private ExpertService expertService;
    public void setExpertService(ExpertService expertService) {
        this.expertService = expertService;
    }

    @Override
    public SubService saveOrUpdate(SubService subService) {
        Optional<SubService> existingSubService = findBySubServiceName(subService.getSubServiceName());
        if (existingSubService.isPresent()) {
            throw new SubServiceAlreadyExistsException(subService.getSubServiceName());
        } else {
            return repository.save(subService);
        }
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

    @Override
    public void addExpertToSubService(Long expertId, Long subServiceId) {
        Optional<SubService> subServiceOptional = repository.findById(subServiceId);
        Optional<Expert> expertOptional = expertService.findById(expertId);

        if (subServiceOptional.isPresent() && expertOptional.isPresent()) {
            SubService subService = subServiceOptional.get();
            Expert expert = expertOptional.get();

            subService.getExperts().add(expert);
            expert.getSubServices().add(subService);

            repository.save(subService);
            expertService.saveOrUpdate(expert);
        } else {
            throw new SubServiceNotFoundException(subServiceId);
        }
    }
}
