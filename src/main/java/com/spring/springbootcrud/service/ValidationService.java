package com.spring.springbootcrud.service;

import com.spring.springbootcrud.domain.exception.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class ValidationService<T> {

  public T validateAndThrow(T entity) {
    final Map<String, String> errors = getErrors(entity);
    if (errors.isEmpty()) return entity;
    throw new ValidationException(errors);
  }

  private Map<String, String> getErrors(T entity) {
    Validator validator = javax.validation.Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<T>> violations = validator.validate(entity);
    Map<String, String> errors = new HashMap<>();
    violations.forEach(violation -> addToErrors(errors, violation));
    return errors;
  }

  private void addToErrors(Map<String, String> errors, ConstraintViolation<T> violation) {
    errors.put(violation.getPropertyPath().toString(), violation.getMessage());
  }
}
