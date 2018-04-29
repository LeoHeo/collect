package com.collect.exception;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {
    super(message);
  }
}
