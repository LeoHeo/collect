package com.collect.web;

import com.collect.dto.user.UserSaveDto;
import com.collect.dto.user.ValidEmailDto;
import com.collect.exception.BadRequestException;
import com.collect.service.UserService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
@RestController
@RequestMapping("/user/**")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public void signUp(@Valid @RequestBody UserSaveDto userSaveDto, BindingResult result) {
    if (result.hasErrors()) {
      throw new BadRequestException("missing required value");
    }

    userService.saveUser(userSaveDto);
  }

  @PostMapping(value = "/valid/email", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ValidEmailDto validEmail(@Valid @RequestBody ValidEmailDto validEmailDto, BindingResult result) {
    if (result.hasErrors()) {
      throw new BadRequestException("missing required value");
    }
    return userService.findByEmail(validEmailDto);
  }

}
