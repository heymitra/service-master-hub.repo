package service;

import exception.InvalidPasswordException;

public interface PasswordService {
    boolean isValidPassword(String password);
    String getPasswordByEmail(String email);
    void updatePasswordByEmail(String email, String newPassword) throws InvalidPasswordException;
    String getPasswordByUserId(Long userId);
    void changePassword(String email, String newPass) throws InvalidPasswordException;
}
