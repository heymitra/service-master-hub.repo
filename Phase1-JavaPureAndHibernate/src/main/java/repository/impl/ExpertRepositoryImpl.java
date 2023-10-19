package repository.impl;

import entity.Expert;
import jakarta.persistence.EntityManager;
import repository.ExpertRepository;

import java.util.Optional;

public class ExpertRepositoryImpl implements ExpertRepository {
    final EntityManager entityManager;

    public ExpertRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Expert> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Expert.class, id));
    }
}
