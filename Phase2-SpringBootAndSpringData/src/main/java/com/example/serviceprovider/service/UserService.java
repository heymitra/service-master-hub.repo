package com.example.serviceprovider.service;

import com.example.serviceprovider.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User update(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteById(User user);
    Page<User> searchAndFilterUsers(String role,
                                    String name,
                                    String surname,
                                    String email,
                                    String sortBy,
                                    Pageable pageable);
}
