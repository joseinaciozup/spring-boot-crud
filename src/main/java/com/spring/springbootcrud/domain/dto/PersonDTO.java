package com.spring.springbootcrud.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PersonDTO implements Serializable {

  UUID id;
  String name;
  String cpf;
  LocalDate bornDate;
  String address;
}
