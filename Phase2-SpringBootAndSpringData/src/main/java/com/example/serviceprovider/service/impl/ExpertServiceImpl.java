package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.service.dto.ExpertDTO;
import com.example.serviceprovider.model.enumeration.ExpertStatusEnum;
import com.example.serviceprovider.repository.ExpertRepository;
import com.example.serviceprovider.service.ExpertService;
import com.example.serviceprovider.validation.LogInfoValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpertServiceImpl implements ExpertService {
    private final ExpertRepository repository;
    private final LogInfoValidator logInfoValidator;

    public ExpertServiceImpl(ExpertRepository repository) {
        this.repository = repository;
        logInfoValidator = new LogInfoValidator();
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
    public Expert saveOrUpdate(Expert expert) {
        try {
            if (logInfoValidator.isValidEmail(expert.getEmail()) && logInfoValidator.isValidPassword(expert.getPassword())) {
                boolean isFirstTimeSave = expert.getId() == null;
                if (isFirstTimeSave && logInfoValidator.isPhotoValid(expert.getPersonalPhoto())) {
                    expert.setRegistrationDateTime(LocalDateTime.now());
                    expert.setScore(0);
                    expert.setExpertStatus(ExpertStatusEnum.NEW);
                }
            }
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        }
        return repository.save(expert);
    }

    @Override
    public Expert approveExpert(Long expertId) {
        Optional<Expert> expertOptional = findById(expertId);
        expertOptional.ifPresent(expert -> {
            expert.setExpertStatus(ExpertStatusEnum.APPROVED);
            saveOrUpdate(expert);
        });
        return expertOptional.orElse(null);
    }
}
