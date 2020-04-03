package com.example.security.jwt.system.exception;

/**
 * @author
 */
public class UserNameAlreadyExistException extends RuntimeException {
    public UserNameAlreadyExistException(String message) {
        super(message);
    }
}
