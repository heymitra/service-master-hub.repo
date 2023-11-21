package com.example.serviceprovider.dto;


import com.example.serviceprovider.model.enumeration.ExpertStatusEnum;
import com.example.serviceprovider.model.enumeration.Role;
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
                             double credit,
                             int rate,
                             ExpertStatusEnum expertStatus,
                             LocalDateTime registrationDateTime,
                             Role role) {
        super(id, name, surname, email, credit, registrationDateTime, role);
        this.rate = rate;
        this.expertStatus = expertStatus;
    }

    private int rate;
    private ExpertStatusEnum expertStatus;
}
