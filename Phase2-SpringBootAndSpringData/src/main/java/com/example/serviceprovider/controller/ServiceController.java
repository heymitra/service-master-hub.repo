package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.ServiceRequestDto;
import com.example.serviceprovider.dto.ServiceResponseDto;
import com.example.serviceprovider.exception.DuplicatedInstanceException;
import com.example.serviceprovider.model.Service;
import com.example.serviceprovider.service.ServiceService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
public class ServiceController {
    private final ServiceService serviceService;
    private final ModelMapper modelMapper;

    public ServiceController(ServiceService serviceService, ModelMapper modelMapper) {
        this.serviceService = serviceService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<ServiceResponseDto> add(@RequestBody @Valid ServiceRequestDto serviceRequestDto) {
        Service service = modelMapper.map(serviceRequestDto, Service.class);
        Service addedService;
        try {
            addedService = serviceService.save(service);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedInstanceException(String.format("%s is duplicated.", service.getServiceName()));
        }
        ServiceResponseDto serviceResponseDto = modelMapper.map(addedService, ServiceResponseDto.class);
        return new ResponseEntity<>(serviceResponseDto, HttpStatus.CREATED);
    }
}
