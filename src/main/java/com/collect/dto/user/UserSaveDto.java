package com.collect.dto.user;

import com.collect.domain.user.Provider;
import com.collect.domain.user.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
  @Email(message = "invalid username format")
  private String email;

  @NotNull
  private Provider provider;

  public User toEntity() {
    return User.builder()
        .username(username)
        .password(password)
        .address(address)
        .provider(provider)
        .email(email)
        .build();
  }
}
