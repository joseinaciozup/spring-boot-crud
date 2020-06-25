package com.spring.springbootcrud.domain.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Person extends Model {

  @NotEmpty(message = "must not be null")
  @Column(nullable = false)
  private String name;

  @NotEmpty(message = "must not be null")
  @Column(nullable = false)
  private String cpf;

  @NotNull(message = "must not be null")
  @Column(name = "born_date")
  private LocalDate bornDate;

  @NotEmpty(message = "must not be null")
  @Column
  private String address;
}
