package com.example.serviceprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ServiceResponseDto {
    private Long id;
    private String serviceName;
}
