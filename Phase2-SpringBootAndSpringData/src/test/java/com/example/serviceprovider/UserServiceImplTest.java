package com.example.serviceprovider;

import com.example.serviceprovider.exception.InvalidFormatException;
import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.model.User;
import com.example.serviceprovider.repository.UserRepository;
import com.example.serviceprovider.service.UserService;
import com.example.serviceprovider.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    // changing password
    @Test
    void changePassword_ValidPassword_PasswordChanged() {
        User user = new Expert();
        user.setPassword("OldValidPassword123");

        String newValidPassword = "NewValidPassword456";

        when(userRepository.save(any(User.class))).thenReturn(user);

        try {
            userService.changePassword(user, newValidPassword);

            assertEquals(newValidPassword, user.getPassword());
        } catch (InvalidFormatException e) {
            fail("Unexpected InvalidFormatException thrown: " + e.getMessage());
        }
    }

    @Test
    void changePassword_InvalidPassword_ThrowsInvalidFormatException() {
        User user = new Expert();
        user.setPassword("OldInvalidPassword");

        String newInvalidPassword = "newpass";

        try {
            userService.changePassword(user, newInvalidPassword);
            fail("Expected InvalidFormatException but no exception was thrown.");
        } catch (InvalidFormatException e) {
            assertEquals("Invalid password format.", e.getMessage());
        }
    }
}




