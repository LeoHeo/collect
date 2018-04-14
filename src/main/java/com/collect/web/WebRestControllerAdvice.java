package com.collect.web;

import com.collect.dto.SimpleMessageDto;
import com.collect.exception.AlreadyRegisterUserException;
import com.collect.exception.AuthenticationException;
import com.collect.exception.BadRequestException;
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

  /**
   * 필수값이 안넘어왔을때 400 던지는 Custom Exception
   */
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public SimpleMessageDto invalidParameterException(BadRequestException ex) {
    return new SimpleMessageDto("missing required value");
  }

  /**
   * 로그인 할때 아이디나 패스워드를 잘못 입력했을때 비 활성화 된 유저가 로그인을 시도할때 401 던지는 Custom Exception
   */
  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public SimpleMessageDto authenticationException(AuthenticationException ex) {
    return new SimpleMessageDto(ex.getMessage());
  }

  /**
   * 이미 가입한 회원에 대한 Exception 처리
   */
  @ExceptionHandler(AlreadyRegisterUserException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public SimpleMessageDto alreadyRegisterUserException(AlreadyRegisterUserException ex) {
    return new SimpleMessageDto(ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public SimpleMessageDto illegalArgumentException(IllegalArgumentException ex) {
    return new SimpleMessageDto(ex.getMessage());
  }
}
