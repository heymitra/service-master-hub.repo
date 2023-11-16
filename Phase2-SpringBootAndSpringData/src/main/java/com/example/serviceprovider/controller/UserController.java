package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.UserResponseDto;
import com.example.serviceprovider.exception.InvalidInputException;
import com.example.serviceprovider.model.User;
import com.example.serviceprovider.model.enumeration.Role;
import com.example.serviceprovider.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'CUSTOMER')")
    @PutMapping("/change-password")
    public ResponseEntity<UserResponseDto> changePassword(@RequestParam String password,
                                                          Principal principal) {
        if (!isPasswordValid(password)) {
            throw new InvalidInputException("Password must be minimum 8 characters, containing at least one lowercase, one uppercase, and one number");
        }

        User user = userService.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User  not found."));

        user.setPassword(password);
        User updatedUser = userService.update(user);
        UserResponseDto userResponseDto = modelMapper.map(updatedUser, UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<Page<UserResponseDto>> searchAndFilterUsers(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "name") String sortBy,
            Pageable pageable
    ) {
        Page<User> users = userService.searchAndFilterUsers(role, name, surname, email, sortBy, pageable);

        List<UserResponseDto> usersDtoList = new ArrayList<>();

        for (User user : users) {
            UserResponseDto userDto = modelMapper.map(user, UserResponseDto.class);
            usersDtoList.add(userDto);
        }

        Page<UserResponseDto> usersDto = new PageImpl<>(usersDtoList, pageable, users.getTotalElements());

        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    private boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        return password.matches(passwordPattern);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'EXPERT')")
    @GetMapping("/activate")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        userService.activate(token);
        return new ResponseEntity<>("Email activated successfully.", HttpStatus.OK);
    }
}
