package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.ExpertRequestDto;
import com.example.serviceprovider.dto.ExpertResponseDto;
import com.example.serviceprovider.exception.DuplicatedInstanceException;
import com.example.serviceprovider.exception.EmptyFileException;
import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.service.ExpertService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/expert")
public class ExpertController {
    private final ExpertService expertService;
    private final ModelMapper modelMapper;

    public ExpertController(ExpertService expertService, ModelMapper modelMapper) {
        this.expertService = expertService;
        this.modelMapper = modelMapper;
    }

    // http://localhost:8080/expert/register
    @PostMapping("/register")
    public ResponseEntity<ExpertResponseDto> register(@ModelAttribute @Valid ExpertRequestDto expertRequestDto) {
        MultipartFile personalPhotoFile = expertRequestDto.getPersonalPhoto();
        byte[] personalPhotoData = null;

        if (personalPhotoFile != null && !personalPhotoFile.isEmpty()) {
            try {
                personalPhotoData = personalPhotoFile.getBytes();
            } catch (IOException e) {
                throw new EmptyFileException("empty image file");
            }
        }
        Expert expert = modelMapper.map(expertRequestDto, Expert.class);
        expert.setPersonalPhoto(personalPhotoData);
        Expert addedExpert;
        try {
            addedExpert = expertService.save(expert);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedInstanceException(String.format("%s is duplicated.", expert.getEmail()));
        }
        ExpertResponseDto expertResponseDto = modelMapper.map(addedExpert, ExpertResponseDto.class);
        return new ResponseEntity<>(expertResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/approve")
    public ResponseEntity<ExpertResponseDto> approve(@RequestParam Long expertId) {
        Expert expert = expertService.approveExpert(expertId);
        ExpertResponseDto expertResponseDto = modelMapper.map(expert, ExpertResponseDto.class);
        return new ResponseEntity<>(expertResponseDto, HttpStatus.OK);
    }
}
