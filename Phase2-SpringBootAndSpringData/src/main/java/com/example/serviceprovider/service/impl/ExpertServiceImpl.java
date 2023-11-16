package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.exception.ItemNotFoundException;
import com.example.serviceprovider.model.UserToken;
import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.model.enumeration.Role;
import com.example.serviceprovider.service.dto.ExpertDTO;
import com.example.serviceprovider.model.enumeration.ExpertStatusEnum;
import com.example.serviceprovider.repository.ExpertRepository;
import com.example.serviceprovider.service.ExpertService;
import com.example.serviceprovider.utility.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExpertServiceImpl implements ExpertService {
    private final ExpertRepository repository;
    private final ImageUtility imageUtility;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailSender mailSender;

    @Override
    public List<ExpertDTO> findAllExperts() {
        return repository.findAllExperts().stream()
                .map(expert -> {
                    ExpertDTO expertDTO = new ExpertDTO();
                    expertDTO.setName(expert.getName());
                    expertDTO.setSurname(expert.getSurname());
                    expertDTO.setEmail(expert.getEmail());
                    expertDTO.setScore(expert.getRate());
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
        expert.setRate(0);
        expert.setExpertStatus(ExpertStatusEnum.NEW);
        expert.setCredit(0);
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        expert.setRole(Role.ROLE_EXPERT);
        expert.setActivated(false);

        Expert savedExpert = repository.save(expert);

        UserToken userToken = new UserToken();
        userToken.setUser(savedExpert);

        mailSender.sendActivationEmail(savedExpert);

        imageUtility.storeExpertPersonalPhoto(expert.getPersonalPhoto(),
                savedExpert.getId() + ".jpg");

        return  savedExpert;
    }

    @Override
    public Expert update(Expert expert) {
        return repository.save(expert);
    }

    @Override
    public Expert approveExpert(Long expertId) {
        Expert expert = findById(expertId)
                .orElseThrow(() -> new ItemNotFoundException("Expert with ID " + expertId + " not found."));
        expert.setExpertStatus(ExpertStatusEnum.APPROVED);
        update(expert);
        return expert;
    }

    @Override
    public Optional<Expert> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void activate(Expert expert) {
        expert.setActivated(true);
        expert.setExpertStatus(ExpertStatusEnum.AWAITING_APPROVAL);
        update(expert);
    }

}
