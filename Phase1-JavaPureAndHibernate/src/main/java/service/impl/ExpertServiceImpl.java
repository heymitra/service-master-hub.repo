package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Expert;
import entity.enumeration.ExpertStatusEnum;
import repository.ExpertRepository;
import repository.dto.ExpertDTO;
import service.ExpertService;

import java.util.List;
import java.util.Optional;

public class ExpertServiceImpl extends BaseServiceImpl<Expert, Long, ExpertRepository>
        implements ExpertService {
    private ExpertRepository repository;

    public ExpertServiceImpl(ExpertRepository repository) {
        super(repository);
    }

    @Override
    public Optional<Expert> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ExpertDTO> findExpertsByStatus(ExpertStatusEnum expertStatus) {
        return repository.findExpertsByStatus(expertStatus);
    }

    @Override
    public List<ExpertDTO> safeLoadAllExperts() {
        return repository.safeLoadAllExperts();
    }
}
