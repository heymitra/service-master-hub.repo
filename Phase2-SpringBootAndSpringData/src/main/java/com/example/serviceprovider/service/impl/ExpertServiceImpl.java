package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.service.dto.ExpertDTO;
import com.example.serviceprovider.model.enumeration.ExpertStatusEnum;
import com.example.serviceprovider.repository.ExpertRepository;
import com.example.serviceprovider.service.ExpertService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpertServiceImpl implements ExpertService {
    private final ExpertRepository repository;

    public ExpertServiceImpl(ExpertRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ExpertDTO> findAllExperts() {
        return repository.findAllExperts().stream()
                .map(expert -> {
                    ExpertDTO expertDTO = new ExpertDTO();
                    expertDTO.setName(expert.getName());
                    expertDTO.setSurname(expert.getSurname());
                    expertDTO.setEmail(expert.getEmail());
                    expertDTO.setScore(expert.getScore());
                    expertDTO.setExpertStatus(expert.getExpertStatus());
                    return expertDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Expert> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Expert save(Expert expert) {
        expert.setRegistrationDateTime(LocalDateTime.now());
        expert.setScore(0);
        expert.setExpertStatus(ExpertStatusEnum.NEW);
        expert.setCredit(0);
        return repository.save(expert);
    }

    @Override
    public Expert update(Expert expert) {
        return repository.save(expert);
    }

    @Override
    public Expert approveExpert(Long expertId) {
        Optional<Expert> expertOptional = findById(expertId);
        expertOptional.ifPresent(expert -> {
            expert.setExpertStatus(ExpertStatusEnum.APPROVED);
            update(expert);
        });
        return expertOptional.orElse(null);
    }
}
