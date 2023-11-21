//package com.example.serviceprovider;
//
//import com.example.serviceprovider.model.Customer;
//import com.example.serviceprovider.repository.CustomerRepository;
//import com.example.serviceprovider.service.CustomerService;
//import com.example.serviceprovider.service.impl.CustomerServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
//@Transactional
//public class CustomerServiceTest {
//    @Mock
//    private CustomerRepository customerRepository;
//
//    private CustomerService customerService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        customerService = new CustomerServiceImpl(customerRepository);
//    }
//
//    @Test
//    void testSaveOrUpdate_FirstTimeRegistration_ValidCustomer() {
//        Customer customer = createValidCustomer(true);
//
//        when(customerRepository.save(customer)).thenReturn(customer);
//
//        Customer savedCustomer = customerService.saveOrUpdate(customer);
//
//        assertNotNull(savedCustomer);
//        assertEquals(customer.getEmail(), savedCustomer.getEmail());
//        assertEquals(customer.getPassword(), savedCustomer.getPassword());
//        assertEquals(LocalDateTime.now().getDayOfYear(), savedCustomer.getRegistrationDateTime().getDayOfYear());
//    }
//
//    @Test
//    void testSaveOrUpdate_UpdateValidCustomer() {
//        Customer customer = createValidCustomer(false);
//
//        when(customerRepository.save(customer)).thenReturn(customer);
//
//        Customer savedCustomer = customerService.saveOrUpdate(customer);
//
//        assertNotNull(savedCustomer);
//        assertEquals(customer.getEmail(), savedCustomer.getEmail());
//        assertEquals(customer.getPassword(), savedCustomer.getPassword());
//    }
//
//    @Test
//    void testSaveOrUpdate_InvalidEmail() {
//        Customer customer = createValidCustomer(true);
//        customer.setEmail("invalid.email");
//
//        Customer savedCustomer = customerService.saveOrUpdate(customer);
//
//        assertNull(savedCustomer);
//    }
//
//    @Test
//    void testSaveOrUpdate_InvalidPassword() {
//        Customer customer = createValidCustomer(true);
//        customer.setPassword("invalidpassword");
//
//        Customer savedCustomer = customerService.saveOrUpdate(customer);
//
//        assertNull(savedCustomer);
//    }
//
//    private Customer createValidCustomer(boolean isFirstTimeRegistration) {
//        Customer customer = new Customer();
//        customer.setEmail("valid.email@example.com");
//        customer.setPassword("ValidPassword1");
//
//        if (isFirstTimeRegistration) {
//            customer.setRegistrationDateTime(null);
//        } else {
//            customer.setRegistrationDateTime(LocalDateTime.now());
//        }
//
//        return customer;
//    }
//}
