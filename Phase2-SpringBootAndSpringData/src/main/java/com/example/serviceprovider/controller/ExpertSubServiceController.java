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
    private final ExpertSubServiceService service;

    public ExpertSubServiceController(ExpertSubServiceService service) {
        this.service = service;
    }

    @PostMapping("/assign")
    public ResponseEntity<ExpertSubServiceResponseDTO> assign(@RequestParam Long expertId,
                                                @RequestParam Long subServiceId) {
        ExpertSubService savedRecord = service.save(expertId, subServiceId);
        if (savedRecord == null) {
            throw new ItemNotFoundException(String.format("Expert-ID = %s / Sub-Service_ID = %s not found", expertId, subServiceId));
        }
        ExpertSubServiceMapper mapper = new ExpertSubServiceMapper();
        ExpertSubServiceResponseDTO savedRecordDTO = mapper.modelToDto(savedRecord);
        return new ResponseEntity<>(savedRecordDTO, HttpStatus.CREATED);

    }

    @DeleteMapping("remove")
    public void delete (@RequestParam Long expertId, @RequestParam Long subServiceId) {
        service.remove(expertId, subServiceId);
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
