package com.collect.web;

import com.collect.domain.user.User;
import com.collect.dto.JwtDto;
import com.collect.dto.user.UserSaveDto;
import com.collect.dto.user.ValidEmailDto;
import com.collect.exception.BadRequestException;
import com.collect.security.CollectAuthenticationUtil;
import com.collect.service.UserService;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
  private final CollectAuthenticationUtil collectAuthenticationUtil;

  public UserController(
      UserService userService,
      CollectAuthenticationUtil collectAuthenticationUtil
  ) {
    this.userService = userService;
    this.collectAuthenticationUtil = collectAuthenticationUtil;
  }

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public JwtDto signUp(@Valid @RequestBody UserSaveDto userSaveDto, BindingResult result) {
    if (result.hasErrors()) {
      throw new BadRequestException("missing required value");
    }

    final String password = userSaveDto.getPassword();
    final User saveUser = userService.saveUser(userSaveDto);

    return new JwtDto(
        collectAuthenticationUtil.createToken(
            saveUser.getEmail(),
            password
        )
    );
  }

  @GetMapping("/signup/social")
  @ResponseStatus(HttpStatus.CREATED)
  public JwtDto socialSignup(OAuth2AuthenticationToken user) {
    final User fundUser = userService.saveOAuth2User(user);
    final Map<String, Object> attributes = user.getPrincipal().getAttributes();

    final String id = Optional.ofNullable(attributes.get("sub")).orElse(attributes.get("id")).toString();
    final String password = id + fundUser.getEmail();

    return new JwtDto(
        collectAuthenticationUtil.createToken(
            fundUser.getEmail(),
            password
        )
    );
  }

  @PostMapping(value = "/valid/email", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ValidEmailDto validEmail(@Valid @RequestBody ValidEmailDto validEmailDto, BindingResult result) {
    if (result.hasErrors()) {
      throw new BadRequestException("missing required value");
    }
    return userService.findByEmail(validEmailDto);
  }

}
