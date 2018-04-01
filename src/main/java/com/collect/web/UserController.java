package com.collect.web;

import com.collect.domain.user.UserSaveDto;
import com.collect.exception.BadRequestException;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
@RestController
@RequestMapping("/user/**")
public class UserController {

  @PostMapping("/signup")
  public void signup(@Valid @RequestBody UserSaveDto userSaveDto, BindingResult result) {
    if (result.hasErrors()) {
      throw new BadRequestException("missing required value");
    }
  }
}
