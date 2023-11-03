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
                .addMapping(src -> src.getProposedPrice(), OrderResponseDto::setProposedPrice)
                .addMapping(src -> src.getWorkDescription(), OrderResponseDto::setWorkDescription)
                .addMapping(src -> src.getCompletionDateTime(), OrderResponseDto::setCompletionDateTime)
                .addMapping(src -> src.getAddress(), OrderResponseDto::setAddress)
                .addMapping(src -> src.getStatus(), OrderResponseDto::setStatus);

        return modelMapper;
    }
}