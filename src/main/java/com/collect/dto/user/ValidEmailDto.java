package com.collect.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-04-14
 */
@Getter
@Setter
@EqualsAndHashCode
public class ValidEmailDto {

  @NotNull
  @Email(
      regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
      message = "invalid email format"
  )
  private String email;
}
