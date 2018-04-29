package com.collect.exception;

/**
 * @author Heo, Jin Han
 * @since 2018-04-06
 */
public class AlreadyRegisterUserException extends RuntimeException {

  public AlreadyRegisterUserException(String message) {
    super(message);
  }
}
