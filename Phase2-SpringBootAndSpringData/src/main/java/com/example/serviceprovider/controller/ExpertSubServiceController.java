package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.ExpertSubServiceResponseDTO;
import com.example.serviceprovider.exception.ItemNotFoundException;
import com.example.serviceprovider.model.ExpertSubService;
import com.example.serviceprovider.service.ExpertSubServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expert-subservice")
public class ExpertSubServiceController {
    private final ExpertSubServiceService expertSubServiceService;

    public ExpertSubServiceController(ExpertSubServiceService expertSubServiceService) {
        this.expertSubServiceService = expertSubServiceService;
    }

    @PostMapping("/assign")
    public ResponseEntity<ExpertSubServiceResponseDTO> assign(@RequestParam Long expertId,
                                                @RequestParam Long subServiceId) {
        ExpertSubService savedRecord = expertSubServiceService.save(expertId, subServiceId);
        if (savedRecord == null) {
            throw new ItemNotFoundException(String.format("Expert-ID = %s / Sub-Service_ID = %s not found", expertId, subServiceId));
        }
        ExpertSubServiceMapper mapper = new ExpertSubServiceMapper();
        ExpertSubServiceResponseDTO savedRecordDTO = mapper.modelToDto(savedRecord);
        return new ResponseEntity<>(savedRecordDTO, HttpStatus.CREATED);

    }
}

class ExpertSubServiceMapper {
    public ExpertSubServiceResponseDTO modelToDto (ExpertSubService expertSubService) {
        return new ExpertSubServiceResponseDTO (expertSubService.getExpert().getId(),
                expertSubService.getExpert().getName(),
                expertSubService.getSubService().getId(),
                expertSubService.getSubService().getSubServiceName());
    }
}
