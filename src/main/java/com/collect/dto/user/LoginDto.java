package com.collect.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-04-06
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class LoginDto {

  private String username;
  private String password;
}
