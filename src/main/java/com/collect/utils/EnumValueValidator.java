package com.collect.utils;

import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
public class EnumValueValidator implements ConstraintValidator<Enum, String > {

  private Enum annotation;

  @Override
  public void initialize(Enum annotation) {
    this.annotation = annotation;
  }

  @Override
  public boolean isValid(String enumValue, ConstraintValidatorContext context) {
    Object[] enumValues = this.annotation.enumClass().getEnumConstants();

    if(enumValues != null) {
      return Stream
          .of(enumValues)
          .anyMatch(v -> enumValue.equals(v.toString()) || (this.annotation.ignoreCase() && enumValue.equalsIgnoreCase(v.toString())));
    }

    return false;
  }
}
