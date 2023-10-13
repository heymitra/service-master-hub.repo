package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Expert;
import entity.SubService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import repository.SubServiceRepository;

import java.util.List;
import java.util.Optional;

public class SubServiceRepositoryImpl extends BaseRepositoryImpl<SubService, Long>
        implements SubServiceRepository {
    public SubServiceRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<SubService> getEntityClass() {
        return SubService.class;
    }

    @Override
    public Optional<SubService> findBySubServiceName(String subServiceName) {
        TypedQuery<SubService> query = entityManager.createQuery(
                        "SELECT s FROM SubService s WHERE s.subServiceName = :name", SubService.class)
                .setParameter("name", subServiceName);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public SubService save(SubService subService) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        super.save(subService);
        transaction.commit();
        return subService;
    }

    @Override
    public List<SubService> findSubServicesByServiceId(Long serviceId) {
        TypedQuery<SubService> query = entityManager.createQuery(
                "SELECT s FROM SubService s WHERE s.service.id = :serviceId", SubService.class);
        query.setParameter("serviceId", serviceId);

        return query.getResultList();
    }

    @Override
    public void addExpert(Expert expert, SubService subService) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        subService.getExperts().add(expert);
        expert.getSubServices().add(subService);
        transaction.commit();
    }

    @Override
    public void removeExpert(Expert expert, SubService subService) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        subService.getExperts().remove(expert);
        expert.getSubServices().remove(subService);
        transaction.commit();
    }
}
