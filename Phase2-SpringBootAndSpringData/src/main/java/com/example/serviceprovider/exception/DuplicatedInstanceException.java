package com.example.serviceprovider.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicatedInstanceException extends DataIntegrityViolationException {

    public DuplicatedInstanceException (String message) {
        super(message);
    }
}