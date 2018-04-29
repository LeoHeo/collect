package com.collect.dto.user;

import com.collect.domain.user.Authority;
import com.collect.domain.user.Provider;
import com.collect.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class UserSaveDto {

  @NotNull
  private String username;

  @NotNull
  private String password;

  @NotNull
  private String address;

  @NotNull
  @Email(
      regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
      message = "invalid email format"
  )
  private String email;

  @NotNull
  private Provider provider = Provider.GENERAL;

  private List<Authority> authorities = new ArrayList<>();

  private boolean enabled = true;

  public User toEntity() {
    return User.builder()
        .username(username)
        .password(password)
        .address(address)
        .provider(provider)
        .email(email)
        .authorities(authorities)
        .enabled(enabled)
        .build();
  }

  @Builder
  public UserSaveDto(
      @NotNull String username,
      @NotNull String password,
      @NotNull @Email(
        regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
        message = "invalid email format"
      ) String email,
      @NotNull Provider provider
  ) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.provider = provider;
  }
}
