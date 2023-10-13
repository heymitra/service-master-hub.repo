package repository;

import entity.Password;

public interface PasswordRepository {
    String getPasswordByEmail(String email);
    void updatePasswordByEmail(String email, String newPassword);
    String getPasswordByUserId(Long userId);
    void changePassword(Password oldPass, String newPass);
}
