package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.UserResponseDto;
import com.example.serviceprovider.exception.InvalidInputException;
import com.example.serviceprovider.model.User;
import com.example.serviceprovider.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PutMapping("/change-password")
    public ResponseEntity<UserResponseDto> changePassword(@RequestParam String password,
                                                          @RequestParam Long userId) {
        if (!isPasswordValid(password)) {
            throw new InvalidInputException("Password must be minimum 8 characters, containing at least one lowercase, one uppercase, and one number");
        }

        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with ID %s does not exist.", userId)));

        user.setPassword(password);
        User updatedUser = userService.update(user);
        UserResponseDto userResponseDto = modelMapper.map(updatedUser, UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    private boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        return password.matches(passwordPattern);
    }

}
