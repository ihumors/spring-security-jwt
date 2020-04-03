package com.example.security.jwt.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 * @author PD
 */
public class UserNotActivatedException extends AuthenticationException {

   private static final long serialVersionUID = -1126699074574529145L;

   public UserNotActivatedException(String message) {
      super(message);
   }
}