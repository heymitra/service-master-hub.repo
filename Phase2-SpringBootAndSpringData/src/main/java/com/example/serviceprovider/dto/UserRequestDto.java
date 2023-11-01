package com.example.serviceprovider.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Surname cannot be blank.")
    private String surname;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must be minimum 8 characters, containing at least one lowercase, one uppercase, and one number")
    private String password;

    @Email(message = "Incorrect email format.")
    private String email;
}
