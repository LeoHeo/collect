package com.collect.web;

import com.collect.dto.JwtDto;
import com.collect.dto.user.LoginDto;
import com.collect.security.CollectAuthenticationUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Heo, Jin Han
 * @since 2018-04-05
 */
@RestController
public class AuthenticationController {

  private final CollectAuthenticationUtil collectAuthenticationUtil;

  public AuthenticationController(CollectAuthenticationUtil collectAuthenticationUtil) {
    this.collectAuthenticationUtil = collectAuthenticationUtil;
  }

  @PostMapping(value = "${jwt.route.authentication.path}")
  public JwtDto createAuthenticationToken(@RequestBody LoginDto loginDto) {

    return new JwtDto(
        collectAuthenticationUtil.createToken(
            loginDto.getUsername(),
            loginDto.getPassword()
        )
    );
  }
}
