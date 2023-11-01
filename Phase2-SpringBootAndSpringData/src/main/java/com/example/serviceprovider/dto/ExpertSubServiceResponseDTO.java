package com.example.serviceprovider.dto;

import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.model.SubService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpertSubServiceResponseDTO {
    Long expertId;
    String expertName;
    Long subServiceId;
    String subServiceName;

}
