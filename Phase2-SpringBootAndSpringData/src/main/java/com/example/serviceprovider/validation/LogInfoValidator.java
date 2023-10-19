package com.example.serviceprovider.validation;

import java.util.regex.Pattern;

public class LogInfoValidator {
    public boolean isValidEmail(String email) {
        if (email == null)
            return false;
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public boolean isValidPassword(String password) {
        if (password == null)
            return false;
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        return password.matches(passwordRegex);
    }

    public boolean isPhotoValid(byte[] photo) {
        return photo.length <= 300 * 1024;
    }
}
