package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Service;
import jakarta.persistence.EntityManager;
import repository.ServiceRepository;

public class ServiceRepositoryImpl extends BaseRepositoryImpl<Service, Long>
        implements ServiceRepository {
    public ServiceRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Service> getEntityClass() {
        return Service.class;
    }
}
