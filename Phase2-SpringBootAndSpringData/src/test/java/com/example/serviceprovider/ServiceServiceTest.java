//package com.example.serviceprovider;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.example.serviceprovider.model.Service;
//import com.example.serviceprovider.repository.ServiceRepository;
//import com.example.serviceprovider.service.impl.ServiceServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
//@Transactional
//class ServiceServiceTest {
//
//    @Mock
//    private ServiceRepository serviceRepository;
//
//    private ServiceServiceImpl serviceService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        serviceService = new ServiceServiceImpl(serviceRepository);
//    }
//
//    @Test
//    void saveOrUpdate_newService_shouldSave() {
//        // Create a new Service object for testing
//        Service service = new Service();
//        service.setServiceName("New Service");
//
//        // Mock the behavior of findByServiceName
//        when(serviceRepository.findByServiceName("New Service")).thenReturn(Optional.empty());
//
//        // Mock the behavior of save method
//        when(serviceRepository.save(service)).thenReturn(service);
//
//        // Call the method to test
//        Service savedService = serviceService.saveOrUpdate(service);
//
//        // Verify that the repository's save method was called with the correct argument
//        verify(serviceRepository).save(service);
//
//        // Assert the returned service is the same as the one passed in
//        assertEquals(service, savedService);
//    }
//
//    @Test
//    void saveOrUpdate_existingService_shouldUpdate() {
//        // Create an existing Service object for testing
//        Service existingService = new Service();
//        existingService.setId(1L);
//        existingService.setServiceName("Existing Service");
//
//        // Mock the behavior of findByServiceName
//        when(serviceRepository.findByServiceName("Existing Service")).thenReturn(Optional.of(existingService));
//
//        // Mock the behavior of save method
//        when(serviceRepository.save(any(Service.class))).thenReturn(existingService);
//
//        // Call the method to test
//        Service updatedService = serviceService.saveOrUpdate(existingService);
//
//        // Verify that the repository's save method was called with the correct argument
//        verify(serviceRepository).save(existingService);
//
//        // Assert the returned service is the same as the one passed in
//        assertEquals(existingService, updatedService);
//    }
//}
//
