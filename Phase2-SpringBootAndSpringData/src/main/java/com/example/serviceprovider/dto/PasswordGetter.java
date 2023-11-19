package com.example.serviceprovider.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PasswordGetter {
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must be minimum 8 characters, containing at least one lowercase, one uppercase, and one number")
    private String password;
}
