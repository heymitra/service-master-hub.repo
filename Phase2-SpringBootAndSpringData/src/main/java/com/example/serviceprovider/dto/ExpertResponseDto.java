package com.example.serviceprovider.dto;


import com.example.serviceprovider.model.enumeration.ExpertStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExpertResponseDto extends UserResponseDto {
    public ExpertResponseDto(Long id,
                             String name,
                             String surname,
                             String email,
                             byte[] personalPhoto,
                             int score,
                             ExpertStatusEnum expertStatus,
                             LocalDateTime registrationDateTime) {
        super(id, name, surname, email, registrationDateTime);
        this.personalPhoto = personalPhoto;
        this.score = score;
        this.expertStatus = expertStatus;
    }

    private byte[] personalPhoto;
    private int score;
    private ExpertStatusEnum expertStatus;
}
