package com.example.serviceprovider;


import com.example.serviceprovider.exception.InvalidFormatException;
import com.example.serviceprovider.exception.SubServiceAlreadyExistsException;
import com.example.serviceprovider.exception.SubServiceNotFoundException;
import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.model.SubService;
import com.example.serviceprovider.model.enumeration.ExpertStatusEnum;
import com.example.serviceprovider.repository.SubServiceRepository;
import com.example.serviceprovider.service.ExpertService;
import com.example.serviceprovider.service.impl.SubServiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
public class SubServiceServiceTest {
    @Mock
    private SubServiceRepository subServiceRepository;

    @Mock
    private ExpertService expertService;

    private SubServiceServiceImpl subServiceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subServiceService = new SubServiceServiceImpl(subServiceRepository);
        subServiceService.setExpertService(expertService);
    }

    // saveOrUpdate sub service
    @Test
    public void testSaveOrUpdateSubService() {
        SubService subService = new SubService();
        subService.setSubServiceName("Test SubService");

        Mockito.when(subServiceRepository.findBySubServiceName(subService.getSubServiceName()))
                .thenReturn(Optional.empty());

        Mockito.when(subServiceRepository.save(subService))
                .thenReturn(subService);

        SubService savedSubService = subServiceService.saveOrUpdate(subService);

        assertNotNull(savedSubService);
        assertEquals("Test SubService", savedSubService.getSubServiceName());
    }


    @Test
    public void testSaveOrUpdateNewSubService() {
        SubService newSubService = new SubService();
        newSubService.setSubServiceName("New SubService");
        when(subServiceRepository.findBySubServiceName(newSubService.getSubServiceName())).thenReturn(Optional.empty());
        when(subServiceRepository.save(newSubService)).thenReturn(newSubService);

        SubService savedSubService = subServiceService.saveOrUpdate(newSubService);

        assertNotNull(savedSubService);
        assertEquals(newSubService.getSubServiceName(), savedSubService.getSubServiceName());
    }

    @Test
    public void testSaveOrUpdateSubServiceAlreadyExists() {
        SubService existingSubService = new SubService();
        existingSubService.setSubServiceName("Existing SubService");
        when(subServiceRepository.findBySubServiceName(existingSubService.getSubServiceName()))
                .thenReturn(Optional.of(existingSubService));

        assertThrows(SubServiceAlreadyExistsException.class, () -> {
            subServiceService.saveOrUpdate(existingSubService);
        });
    }


    // findSubServicesByServiceId
    @Test
    public void testFindSubServicesByServiceId2() {
        Long serviceId = 1L;
        SubService subService1 = new SubService();
        subService1.setId(1L);
        SubService subService2 = new SubService();
        subService2.setId(2L);
        List<SubService> subServices = new ArrayList<>();
        subServices.add(subService1);
        subServices.add(subService2);

        when(subServiceRepository.findSubServicesByServiceId(serviceId)).thenReturn(subServices);

        List<SubService> result = subServiceService.findSubServicesByServiceId(serviceId);

        assertEquals(2, result.size());
        assertEquals(subService1.getId(), result.get(0).getId());
        assertEquals(subService2.getId(), result.get(1).getId());
    }


    // addExpertToSubService
    @Test
    public void testAddExpertToSubService() throws InvalidFormatException {
        Long expertId = 1L;
        Long subServiceId = 2L;

        SubService subService = new SubService();
        subService.setSubServiceName("SubService 1");
        Expert expert = new Expert();
        expert.setExpertStatus(ExpertStatusEnum.NEW);

        Mockito.when(subServiceRepository.findById(subServiceId))
                .thenReturn(Optional.of(subService));

        Mockito.when(expertService.findById(expertId))
                .thenReturn(Optional.of(expert));

        Mockito.when(subServiceRepository.save(subService))
                .thenReturn(subService);

        Mockito.when(expertService.saveOrUpdate(expert))
                .thenReturn(expert);

        subServiceService.addExpertToSubService(expertId, subServiceId);

        assertTrue(subService.getExperts().contains(expert));
        assertTrue(expert.getSubServices().contains(subService));

    }


    @Test
    public void testAddExpertToSubService2() throws InvalidFormatException {
        Long subServiceId = 1L;
        Long expertId = 2L;

        SubService subService = new SubService();
        subService.setId(subServiceId);
        Expert expert = new Expert();
        expert.setId(expertId);

        when(subServiceRepository.findById(subServiceId)).thenReturn(Optional.of(subService));
        when(expertService.findById(expertId)).thenReturn(Optional.of(expert));
        when(subServiceRepository.save(subService)).thenReturn(subService);
        when(expertService.saveOrUpdate(expert)).thenReturn(expert);

        subServiceService.addExpertToSubService(expertId, subServiceId);

        assertTrue(subService.getExperts().contains(expert));
        assertTrue(expert.getSubServices().contains(subService));
    }

    @Test
    public void testAddExpertToSubServiceSubServiceNotFound() {
        Long subServiceId = 1L;
        Long expertId = 2L;

        when(subServiceRepository.findById(subServiceId)).thenReturn(Optional.empty());

        assertThrows(SubServiceNotFoundException.class, () -> {
            subServiceService.addExpertToSubService(expertId, subServiceId);
        });
    }
}
