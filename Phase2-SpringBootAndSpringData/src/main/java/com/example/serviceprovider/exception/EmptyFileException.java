package com.example.serviceprovider.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class EmptyFileException extends DataIntegrityViolationException {

    public EmptyFileException (String message) {
        super(message);
    }
}
