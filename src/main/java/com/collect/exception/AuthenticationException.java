package com.collect.exception;

/**
 * @author Heo, Jin Han
 * @since 2018-04-05
 */
public class AuthenticationException extends RuntimeException {
  public AuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }
}
