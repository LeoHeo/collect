package com.collect.web;

import com.collect.exception.BadRequestException;
import com.collect.exception.SingleErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
@RestControllerAdvice
public class WebRestControllerAdvice {

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public SingleErrorMessage invalidParameterException(BadRequestException err) {
    return new SingleErrorMessage("missing required value");
  }
}
