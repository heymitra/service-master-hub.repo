package com.example.serviceprovider.config;

import com.example.serviceprovider.base.BaseEntity;
import com.example.serviceprovider.dto.UserResponseDto;
import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Expert.class, UserResponseDto.class)
                .addMapping(BaseEntity::getId, UserResponseDto::setId)
                .addMapping(User::getName, UserResponseDto::setName)
                .addMapping(User::getSurname, UserResponseDto::setSurname)
                .addMapping(User::getEmail, UserResponseDto::setEmail)
                .addMapping(User::getRegistrationDateTime, UserResponseDto::setRegistrationDateTime);


        return modelMapper;
    }
}