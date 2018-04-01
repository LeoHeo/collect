package com.collect.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
public enum Provider {
  GENERAL,
  FACEBOOK,
  GOOGLE;

  @JsonCreator
  public static Provider create(String requestValue) {
    return Stream.of(values())
        .filter(v -> v.toString().equalsIgnoreCase(requestValue))
        .findFirst()
        .orElse(null);
  }
}
