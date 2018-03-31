package com.collect.domain.user;

import com.collect.utils.Enum;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
@Getter
@Setter
@NoArgsConstructor
public class UserSaveDto {

  @NotNull
  @Email(message = "invalid email format")
  private String email;

  @NotNull
  private String password;

  @NotNull
  private String address;

  @NotNull
  @Enumerated(EnumType.STRING)
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
