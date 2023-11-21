package com.example.serviceprovider.dto;

import com.example.serviceprovider.model.ExpertSubService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpertSubServiceResponseDto {
    Long expertId;
    String expertName;
    Long subServiceId;
    String subServiceName;

    public ExpertSubServiceResponseDto modelToDto (ExpertSubService expertSubService) {
        return new ExpertSubServiceResponseDto(expertSubService.getExpert().getId(),
                expertSubService.getExpert().getName(),
                expertSubService.getSubService().getId(),
                expertSubService.getSubService().getSubServiceName());
    }
}
