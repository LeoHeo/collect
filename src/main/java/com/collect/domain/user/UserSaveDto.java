package com.collect.domain.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
@Getter
@Setter
@Data
public class UserSaveDto {

  @NotNull
  @Email(message = "invalid email format")
  private String email;

  @NotNull
  private String password;

  @NotNull
  private String address;

  @NotNull
  private Provider provider;

  public User toEntity() {
    return User.builder()
        .email(email)
        .password(password)
        .address(address)
        .provider(provider)
        .build();
  }
}
