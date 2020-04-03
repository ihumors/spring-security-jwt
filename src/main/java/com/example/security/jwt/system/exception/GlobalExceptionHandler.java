package com.example.security.jwt.system.exception;

import com.example.security.jwt.security.exception.JWTTokenExpiredException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author PD
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserNameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleUserNameAlreadyExistException(UserNameAlreadyExistException e) {
        return new ErrorMessage(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ExceptionHandler(value = SignatureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleSignatureException(SignatureException e) {
        return new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler(value = JWTTokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage handleJWTTokenExpiredException(SignatureException e) {
        return new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

}
