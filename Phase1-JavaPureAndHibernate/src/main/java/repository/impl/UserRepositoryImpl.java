package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import repository.UserRepository;
import repository.dto.ExpertDTO;

import java.util.List;

public class UserRepositoryImpl extends BaseRepositoryImpl<User, Long>
        implements UserRepository {
    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<User> getEntityClass() {
        return null;
    }

    public User findUserByEmail(String email) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // User not found for the given email
        }
    }

    @Override
    public List<ExpertDTO> safeLoadAllExperts() {
        TypedQuery<ExpertDTO> query = entityManager.createQuery(
                "SELECT new repository.dto.ExpertDTO(e.id, e.name, e.surname, e.email, e.personalPhoto, e.score, e.expertStatus) FROM Expert e",
                ExpertDTO.class);
        return query.getResultList();
    }

    @Override
    public void changePassword(User user, String newPass) {
        user.setPassword(newPass);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        transaction.commit();
    }
}
