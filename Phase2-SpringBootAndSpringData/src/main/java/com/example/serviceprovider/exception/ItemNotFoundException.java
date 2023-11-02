package com.example.serviceprovider.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class ItemNotFoundException extends DataIntegrityViolationException {

    public ItemNotFoundException(String message) {
        super(message);
    }
}
