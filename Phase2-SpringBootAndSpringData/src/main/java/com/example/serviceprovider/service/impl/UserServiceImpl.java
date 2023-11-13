package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.User;
import com.example.serviceprovider.repository.UserRepository;
import com.example.serviceprovider.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User update(User user){
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


    public Page<User> searchAndFilterUsers(String role,
                                           String name,
                                           String surname,
                                           String email,
                                           String sortBy,
                                           Pageable pageable) {
        return repository.searchAndFilterUsers(role, name, surname, email, sortBy, pageable);
    }
}
