package com.example.serviceprovider.dto;

import com.example.serviceprovider.validation.FileSize;
import com.example.serviceprovider.validation.ImageFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ExpertRequestDto extends UserRequestDto {
    @FileSize(max = 300 * 1024, message = "Personal photo size should not exceed 300KB")
    @ImageFormat(message = "Personal photo must be in .jpg format")
    private MultipartFile personalPhoto;

    public ExpertRequestDto(String name,
                            @NotBlank(message = "Surname cannot be blank.") String surname,
                            @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
                                    message = "Password must be minimum 8 characters, containing at least one lowercase, one uppercase, and one number") String password,
                            @Email(message = "Incorrect email format.") String email,
                            MultipartFile personalPhoto) {
        super(name, surname, password, email);
        this.personalPhoto = personalPhoto;
    }
}

