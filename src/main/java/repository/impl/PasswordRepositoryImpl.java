package repository.impl;

import entity.Password;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import repository.PasswordRepository;

public class PasswordRepositoryImpl implements PasswordRepository {

    final EntityManager entityManager;

    public PasswordRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public String getPasswordByEmail(String email) {
        try {
            String jpql = "SELECT u.password FROM User u WHERE u.email = :email";
            Password password = entityManager.createQuery(jpql, Password.class)
                    .setParameter("email", email)
                    .getSingleResult(); //PasswordRepositoryImpl.java:22

            // Assuming Password entity has a method to retrieve the password
            return password != null ? password.getPassword() : null;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPasswordByUserId(Long userId) {
        Password password = entityManager.find(Password.class, userId);
        return (password != null) ? password.getPassword() : null;
    }

    @Override
    public void changePassword(Password oldPass, String newPass) {
        oldPass.setPassword(newPass);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        transaction.commit();
    }

    @Override
    public void updatePasswordByEmail(String email, String newPassword) {
        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();

            Password password = user.getPassword();
            password.setPassword(newPassword);

            entityManager.merge(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
