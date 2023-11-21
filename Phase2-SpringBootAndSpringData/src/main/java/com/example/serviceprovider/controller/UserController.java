package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.PasswordGetter;
import com.example.serviceprovider.dto.UserReportDto;
import com.example.serviceprovider.dto.UserResponseDto;
import com.example.serviceprovider.model.User;
import com.example.serviceprovider.model.enumeration.Role;
import com.example.serviceprovider.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
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
    public ResponseEntity<UserResponseDto> changePassword(@RequestBody @Valid PasswordGetter password,
                                                          Principal principal) {
        User user = userService.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        user.setPassword(password.getPassword());
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

    @PreAuthorize("hasAnyRole('CUSTOMER', 'EXPERT')")
    @GetMapping("/activate")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        userService.activate(token);
        return new ResponseEntity<>("Email activated successfully.", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'EXPERT')")
    @GetMapping("/credit")
    public ResponseEntity<Double> getUserCredit(@AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
        double userCredit = user.getCredit();
        return ResponseEntity.ok(userCredit);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/expert-report")
    public List<UserReportDto> getExpertsReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime registrationTimeStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime registrationTimeEnd,
            @RequestParam(required = false) Integer minOrders,
            @RequestParam(required = false) Integer maxOrders) {
        return userService.getExpertReportDTO(registrationTimeStart, registrationTimeEnd, minOrders, maxOrders);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/customer-report")
    public List<UserReportDto> getCustomersReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime registrationTimeStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime registrationTimeEnd,
            @RequestParam(required = false) Integer minOrders,
            @RequestParam(required = false) Integer maxOrders) {
        return userService.getCustomersReportDTO(registrationTimeStart, registrationTimeEnd, minOrders, maxOrders);
    }
}
