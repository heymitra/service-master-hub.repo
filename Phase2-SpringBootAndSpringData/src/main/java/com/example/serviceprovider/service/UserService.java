package com.example.serviceprovider.service;

import com.example.serviceprovider.exception.InvalidFormatException;
import com.example.serviceprovider.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteById(User user);
    void changePassword(User user, String newPass) throws InvalidFormatException;
    boolean isEmailUnique(String email);
}
