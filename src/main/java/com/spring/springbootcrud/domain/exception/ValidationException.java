package com.spring.springbootcrud.domain.exception;

import java.util.Map;
import lombok.Getter;

public class ValidationException extends RuntimeException {

  @Getter private final Map<String, String> errors;

  public ValidationException(Map<String, String> errors) {
    super(errors.toString());
    this.errors = errors;
  }
}
