package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.dto.UserReportDto;
import com.example.serviceprovider.exception.InvalidInputException;
import com.example.serviceprovider.model.Customer;
import com.example.serviceprovider.model.UserToken;
import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.model.User;
import com.example.serviceprovider.model.enumeration.Role;
import com.example.serviceprovider.repository.UserRepository;
import com.example.serviceprovider.service.CustomerService;
import com.example.serviceprovider.service.UserTokenService;
import com.example.serviceprovider.service.ExpertService;
import com.example.serviceprovider.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserTokenService userTokenService;
    private final ExpertService expertService;
    private final CustomerService customerService;

    @Override
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(User user) {
        repository.deleteById(user.getId());
    }

    @Override
    public Page<User> searchAndFilterUsers(Role role,
                                           String name,
                                           String surname,
                                           String email,
                                           String sortBy,
                                           Pageable pageable) {
        return repository.searchAndFilterUsers(role, name, surname, email, sortBy, pageable);
    }

    public List<UserReportDto> getCustomersReportDTO(LocalDateTime registrationTimeStart,
                                                     LocalDateTime registrationTimeEnd,
                                                     Integer minOrders,
                                                     Integer maxOrders) {
        return repository.getCustomersReportDTO(registrationTimeStart, registrationTimeEnd, minOrders, maxOrders);
    }

    public List<UserReportDto> getExpertReportDTO(LocalDateTime registrationTimeStart,
                                                  LocalDateTime registrationTimeEnd,
                                                  Integer minOrders,
                                                  Integer maxOrders) {
        return repository.getExpertReportDTO(registrationTimeStart, registrationTimeEnd, minOrders, maxOrders);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void activate(String token) {
        UserToken emailActivator = userTokenService.findByActivationToken(token)
                .orElseThrow(() -> new InvalidInputException("Invalid or expired verification code."));

        User user = emailActivator.getUser();
        userTokenService.delete(emailActivator);

        if (user.getRole().equals(Role.ROLE_EXPERT)) {
            expertService.activate((Expert) user);
        } else if (user.getRole().equals(Role.ROLE_CUSTOMER)) {
            customerService.activate((Customer) user);
        }
    }
}
