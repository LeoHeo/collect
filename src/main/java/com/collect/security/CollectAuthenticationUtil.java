package com.collect.security;

import com.collect.exception.AuthenticationException;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author Heo, Jin Han
 * @since 2018-04-28
 */
@Component
@AllArgsConstructor
public class CollectAuthenticationUtil {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserDetailsService userDetailsService;

  public String createToken(String username, String password) {
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);

    authenticate(username, password);

    // Reload password post-security so we can generate the token
    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    return jwtTokenUtil.generateToken(userDetails);
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
