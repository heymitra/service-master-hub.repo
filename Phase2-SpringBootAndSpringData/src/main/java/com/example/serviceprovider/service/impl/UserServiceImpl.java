package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.User;
import com.example.serviceprovider.repository.UserRepository;
import com.example.serviceprovider.service.UserService;
import com.example.serviceprovider.validation.LogInfoValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    LogInfoValidator logInfoValidator;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
        logInfoValidator = new LogInfoValidator();
    }

    @Override
    public User update(User user){
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
}
