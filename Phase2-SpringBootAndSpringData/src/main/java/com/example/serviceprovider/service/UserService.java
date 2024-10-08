package com.example.serviceprovider.service;

import com.example.serviceprovider.dto.UserReportDto;
import com.example.serviceprovider.model.User;
import com.example.serviceprovider.model.enumeration.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User update(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    void deleteById(User user);

    Page<User> searchAndFilterUsers(Role role,
                                    String name,
                                    String surname,
                                    String email,
                                    String sortBy,
                                    Pageable pageable);

    Optional<User> findByEmail(String email);

    void activate(String token);

    List<UserReportDto> getExpertReportDTO(LocalDateTime registrationTimeStart,
                                           LocalDateTime registrationTimeEnd,
                                           Integer minOrders,
                                           Integer maxOrders);

    List<UserReportDto> getCustomersReportDTO(LocalDateTime registrationTimeStart,
                                              LocalDateTime registrationTimeEnd,
                                              Integer minOrders,
                                              Integer maxOrders);
}
