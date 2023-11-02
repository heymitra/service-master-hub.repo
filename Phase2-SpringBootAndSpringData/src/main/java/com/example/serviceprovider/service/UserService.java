package com.example.serviceprovider.service;

import com.example.serviceprovider.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User update(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteById(User user);
}
