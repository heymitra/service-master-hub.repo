package com.example.serviceprovider.service;

import com.example.serviceprovider.model.UserToken;

import java.util.Optional;

public interface UserTokenService {
    UserToken save(UserToken userToken);
    Optional<UserToken> findByActivationToken(String activationToken);
    void delete (UserToken userToken);
}
