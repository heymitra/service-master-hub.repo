package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.UserToken;
import com.example.serviceprovider.repository.UserTokenRepository;
import com.example.serviceprovider.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserTokenServiceImpl implements UserTokenService {
    private final UserTokenRepository repository;

    @Override
    public UserToken save(UserToken userToken) {
        return repository.save(userToken);
    }

    @Override
    public Optional<UserToken> findByActivationToken(String activationToken) {
        return repository.findByActivationToken(activationToken);
    }

    @Override
    public void delete(UserToken userToken) {
        repository.delete(userToken);
    }
}
