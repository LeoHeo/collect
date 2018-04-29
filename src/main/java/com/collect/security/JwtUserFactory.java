package com.collect.security;

import static java.util.stream.Collectors.toList;

import com.collect.domain.user.Authority;
import com.collect.domain.user.User;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author Heo, Jin Han
 * @since 2018-04-05
 */
public final class JwtUserFactory {

  private JwtUserFactory() {
  }

  public static JwtUser create(User user) {
    return new JwtUser(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        mapToGrantedAuthorities(user.getAuthorities()),
        user.isEnabled()
    );
  }

  private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
    return authorities.stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
        .collect(toList());
  }

}
