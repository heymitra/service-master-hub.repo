package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Expert;
import entity.enumeration.ExpertStatusEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.ExpertRepository;
import repository.dto.ExpertDTO;

import java.util.List;
import java.util.Optional;

public class ExpertRepositoryImpl extends BaseRepositoryImpl<Expert, Long>
        implements ExpertRepository {
    public ExpertRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Expert> getEntityClass() {
        return Expert.class;
    }

    @Override
    public Optional<Expert> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Expert.class, id));
    }

    @Override
    public List<ExpertDTO> findExpertsByStatus(ExpertStatusEnum expertStatus) {
        TypedQuery<ExpertDTO> query = entityManager.createQuery(
                "SELECT new repository.dto.ExpertDTO(e.id, e.name, e.surname, e.email, e.personalPhoto, e.score, e.expertStatus) " +
                        "FROM Expert e " +
                        "WHERE e.expertStatus = :status",
                ExpertDTO.class);

        query.setParameter("status", expertStatus);

        return query.getResultList();
    }

    @Override
    public List<ExpertDTO> safeLoadAllExperts() {
        TypedQuery<ExpertDTO> query = entityManager.createQuery(
                "SELECT new repository.dto.ExpertDTO(e.id, e.name, e.surname, e.email, e.personalPhoto, e.score, e.expertStatus) FROM Expert e",
                ExpertDTO.class);
        return query.getResultList();
    }
}
