package com.collect.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Heo, Jin Han
 * @since 2018-04-04
 */
@Getter
@EqualsAndHashCode
public class JwtUser implements UserDetails {

  private final Long id;
  private final String username;
  private final String password;
  private final String email;
  private final Collection<? extends GrantedAuthority> authorities;
  private final boolean enabled;

  public JwtUser(
      Long id,
      String username,
      String email,
      String password,
      Collection<? extends GrantedAuthority> authorities,
      boolean enabled
  ) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.enabled = enabled;
  }

  @JsonIgnore
  public Long getId() {
    return id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
