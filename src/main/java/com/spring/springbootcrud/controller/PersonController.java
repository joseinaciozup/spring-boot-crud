package com.spring.springbootcrud.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.spring.springbootcrud.domain.dto.PersonDTO;
import com.spring.springbootcrud.service.DocumentService;
import com.spring.springbootcrud.service.PersonService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@DefaultProperties(
    groupKey = "person",
    commandProperties =
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "800"))
@RestController
@RequestMapping(value = "person", produces = APPLICATION_JSON_VALUE)
public class PersonController {

  @Autowired private PersonService personService;
  @Autowired private DocumentService documentService;

  @PostMapping
  @HystrixCommand(commandKey = "save-new")
  public ResponseEntity<PersonDTO> saveNew(@RequestBody PersonDTO personDTO) {
    return ResponseEntity.ok(personService.save(personDTO));
  }

  @GetMapping
  @HystrixCommand(commandKey = "find-by-filter")
  public ResponseEntity<List<PersonDTO>> findByFilter(
      @RequestParam(name = "cpf", required = false) String cpf,
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "address", required = false) String address) {
    final PersonDTO filter =
        PersonDTO.builder()
            .cpf(documentService.cleanDocument(cpf))
            .name(name)
            .address(address)
            .build();
    return ResponseEntity.ok(personService.findByFilter(filter));
  }

  @GetMapping("{id}")
  @HystrixCommand(commandKey = "find-by-id")
  public ResponseEntity<PersonDTO> findById(@PathVariable("id") UUID id) {
    return personService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @DeleteMapping("{id}")
  @HystrixCommand(commandKey = "delete-by-id")
  public ResponseEntity<PersonDTO> deleteById(@PathVariable("id") UUID id) {
    return personService
        .cancelById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
