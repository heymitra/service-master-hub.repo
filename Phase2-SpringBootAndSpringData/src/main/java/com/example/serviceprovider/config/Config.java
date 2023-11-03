package com.example.serviceprovider.config;

import com.example.serviceprovider.dto.OrderResponseDto;
import com.example.serviceprovider.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Order.class, OrderResponseDto.class)
                .addMapping(Order::getProposedPrice, OrderResponseDto::setProposedPrice)
                .addMapping(Order::getWorkDescription, OrderResponseDto::setWorkDescription)
                .addMapping(Order::getCompletionDateTime, OrderResponseDto::setCompletionDateTime)
                .addMapping(Order::getAddress, OrderResponseDto::setAddress)
                .addMapping(Order::getStatus, OrderResponseDto::setStatus);

        return modelMapper;
    }
}