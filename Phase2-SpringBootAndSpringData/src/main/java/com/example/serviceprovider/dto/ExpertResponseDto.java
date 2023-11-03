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
//                             byte[] personalPhoto,
                             double credit,
                             int rate,
                             ExpertStatusEnum expertStatus,
                             LocalDateTime registrationDateTime) {
        super(id, name, surname, email, credit, registrationDateTime);
//        this.personalPhoto = personalPhoto;
        this.rate = rate;
        this.expertStatus = expertStatus;
    }

//    private byte[] personalPhoto;
    private int rate;
    private ExpertStatusEnum expertStatus;
}
