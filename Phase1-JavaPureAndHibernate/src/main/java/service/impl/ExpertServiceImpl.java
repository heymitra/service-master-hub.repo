package service.impl;

import entity.Expert;
import repository.ExpertRepository;
import service.ExpertService;

import java.util.Optional;

public class ExpertServiceImpl implements ExpertService {
    private ExpertRepository repository;

    public ExpertServiceImpl(ExpertRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Expert> findById(Long id) {
        return repository.findById(id);
    }
}
