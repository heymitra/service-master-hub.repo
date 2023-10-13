package service.impl;

import entity.User;
import exception.InvalidPasswordException;
import jakarta.persistence.NoResultException;
import repository.PasswordRepository;
import service.PasswordService;
import util.ApplicationContext;

public class PasswordServiceImpl implements PasswordService {

    private PasswordRepository repository;

    public PasswordServiceImpl(PasswordRepository repository) {
        this.repository = repository;
    }

    public PasswordServiceImpl() {
    }

    @Override
    public boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        return password.matches(passwordRegex);
    }

    @Override
    public String getPasswordByEmail(String email) {
        return repository.getPasswordByEmail(email);
    }

    @Override
    public void updatePasswordByEmail(String email, String newPassword) throws InvalidPasswordException {
        if (!isValidPassword(newPassword)) {
            throw new InvalidPasswordException("Invalid password format.");
        } else
            repository.updatePasswordByEmail(email, newPassword);
    }

    @Override
    public String getPasswordByUserId(Long userId) {
        return repository.getPasswordByUserId(userId);
    }

    @Override
    public void changePassword(String email, String newPass) throws InvalidPasswordException {
        if (!isValidPassword(newPass))
            throw new InvalidPasswordException("Invalid password format.");
        User user = null;
        try {
            user = ApplicationContext.getUserService().findUserByEmail(email);
        } catch (NoResultException e) {
            System.out.println("User not found for the given email.");
        }
        repository.changePassword(user.getPassword(), newPass);

    }
}
