package com.collect.web;

import com.collect.exception.AuthenticationException;
import com.collect.dto.JwtDto;
import com.collect.security.JwtTokenUtil;
import com.collect.dto.user.LoginDto;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Heo, Jin Han
 * @since 2018-04-05
 */
@RestController
public class AuthenticationController {

  @Value("${jwt.header}")
  private String tokenHeader;

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserDetailsService userDetailsService;

  public AuthenticationController(
      AuthenticationManager authenticationManager,
      JwtTokenUtil jwtTokenUtil,
      @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService
  ) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userDetailsService = userDetailsService;
  }

  @PostMapping(value = "${jwt.route.authentication.path}")
  public JwtDto createAuthenticationToken(@RequestBody LoginDto loginDto) {

    authenticate(loginDto.getUsername(), loginDto.getPassword());

    // Reload password post-security so we can generate the token
    final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUsername());
    final String token = jwtTokenUtil.generateToken(userDetails);

    // Return the token
    return new JwtDto(token);
  }


  private void authenticate(String username, String password) {
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException ex) {
      throw new AuthenticationException("User is disabled", ex);
    } catch (BadCredentialsException ex) {
      throw new AuthenticationException("unauthorized", ex);
    }
  }
}
