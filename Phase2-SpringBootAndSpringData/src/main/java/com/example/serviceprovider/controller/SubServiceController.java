package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.SubServiceRequestDto;
import com.example.serviceprovider.dto.SubServiceResponseDto;
import com.example.serviceprovider.exception.DuplicatedInstanceException;
import com.example.serviceprovider.exception.InvalidInputException;
import com.example.serviceprovider.exception.ItemNotFoundException;
import com.example.serviceprovider.model.SubService;
import com.example.serviceprovider.service.SubServiceService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subservice")
public class SubServiceController {
    private final SubServiceService subServiceService;
    private final ModelMapper modelMapper;

    public SubServiceController(SubServiceService subServiceService, ModelMapper modelMapper) {
        this.subServiceService = subServiceService;
        this.modelMapper = modelMapper;
    }


    // http://localhost:8080/subservice/add
    @PostMapping("/add")
    public ResponseEntity<SubServiceResponseDto> add(@RequestBody @Valid SubServiceRequestDto subServiceRequestDto,
                                                     @RequestParam Long serviceId) {
        SubService subService = modelMapper.map(subServiceRequestDto, SubService.class);
        SubService addedSubService;
        try {
            addedSubService = subServiceService.save(subService, serviceId);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedInstanceException(String.format("%s is duplicated.", subService.getSubServiceName()));
        }
        SubServiceResponseDto subServiceResponseDto = modelMapper.map(addedSubService, SubServiceResponseDto.class);
        return new ResponseEntity<>(subServiceResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<SubServiceResponseDto> edit(@RequestParam Long subServiceId,
                                                      @RequestParam String newDescription,
                                                      @RequestParam double newBasePrice) {
        SubService subService = subServiceService.findById(subServiceId)
                .orElseThrow(() -> new ItemNotFoundException("Sub-service with ID " + subServiceId + " not found."));
        if (newBasePrice < 0)
            throw new InvalidInputException("Price must be a positive number.");
        subService.setDescription(newDescription);
        subService.setBasePrice(newBasePrice);

        SubService updatedSubService = subServiceService.update(subService);
        SubServiceResponseDto subServiceResponseDto = modelMapper.map(updatedSubService, SubServiceResponseDto.class);

        return new ResponseEntity<>(subServiceResponseDto, HttpStatus.OK);
    }
}
