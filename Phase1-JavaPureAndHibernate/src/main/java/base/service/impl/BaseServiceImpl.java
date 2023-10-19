package base.service.impl;

import base.entity.BaseEntity;
import base.repository.BaseRepository;
import base.service.BaseService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import validation.EntityValidator;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BaseServiceImpl
        <E extends BaseEntity<ID>,
                ID extends Serializable,
                R extends BaseRepository<E, ID>>
        implements BaseService<E, ID> {

    protected final R repository;
    protected final Validator validator;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
        this.validator = EntityValidator.validator;
    }


    @Override
    public E save(E entity) {
        if (!isValid(entity))
            return null;
        try {
            repository.beginTransaction();
            repository.save(entity);
            repository.commitTransaction();
        } catch (Exception e) {
            repository.rollBack();
            return null;
        }
        return entity;
    }

    @Override
    public void delete(E entity) {
        if (!isValid(entity))
            return;
        if (repository.contain(entity)) {
            try {
                repository.beginTransaction();
                repository.delete(entity);
                repository.commitTransaction();
            } catch (Exception e) {
                repository.rollBack();
            }
        } else {
            throw new RuntimeException("This entity is not saved.");
        }
    }

    @Override
    public E update(E entity) {
        if (!isValid(entity))
            return null;
        try {
            repository.beginTransaction();
            repository.update(entity);
            repository.commitTransaction();
        } catch (Exception e) {
            repository.rollBack();
            return entity;
        }
        return entity;
    }

    @Override
    public Optional<E> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<E> loadAll() {
        return repository.loadAll();
    }

    @Override
    public boolean contain(E entity) {
        return repository.contain(entity);
    }

    @Override
    public boolean contain(ID id) {
        return repository.contain(id);
    }

    @Override
    public boolean isValid(E entity) {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<E> p : violations)
                System.out.println(p.getMessage());
            return false;
        }
        return true;
    }
}
