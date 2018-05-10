package com.collect.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class SimpleMessageDto {
  private final String message;
}
