package com.example.security.jwt.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown in case of a token expired user trying to authenticate.
 * @author PD
 */
public class JWTTokenExpiredException extends AuthenticationException {

    private static final long serialVersionUID = -8468754593200372586L;

    public JWTTokenExpiredException(String message) {
        super(message);
    }
}
