package com.spring.springbootcrud.service;

import static com.spring.springbootcrud.domain.repository.PersonRepository.getSpecification;
import static java.time.LocalDateTime.now;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

import com.spring.springbootcrud.domain.dto.PersonDTO;
import com.spring.springbootcrud.domain.entity.Person;
import com.spring.springbootcrud.domain.mapper.PersonMapper;
import com.spring.springbootcrud.domain.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonService {

  @Autowired private DocumentService documentService;
  @Autowired private PersonMapper personMapper;
  @Autowired private PersonRepository personRepository;
  @Autowired private ValidationService<Person> validationService;

  public PersonDTO save(PersonDTO personDTO) {
    return ofNullable(personDTO)
        .map(personToEntity().andThen(validationService::validateAndThrow).andThen(cleanCpf()))
        .map(persist().andThen(logPersistSuccess()))
        .map(personMapper::toDTO)
        .orElseThrow(() -> new RuntimeException("Falha salvar a Pessoa"));
  }

  public Optional<PersonDTO> findById(UUID id) {
    return ofNullable(id).map(findByIdAndMapperToDTO()).orElseGet(Optional::empty);
  }

  public Optional<PersonDTO> cancelById(UUID id) {

    return ofNullable(id)
        .map(
            uuid1 ->
                personRepository
                    .findByIdAndEnabledTrue(uuid1)
                    .map(
                        person -> {
                          person.setDeletedAt(now());
                          person.setEnabled(false);
                          return person;
                        })
                    .map(personRepository::save)
                    .map(personMapper::toDTO))
        .orElse(empty());

    /*if (id != null)
      return personRepository
          .findByIdAndEnabledTrue(id)
          .map(
              person -> {
                person.setDeletedAt(now());
                person.setEnabled(false);
                return person;
              })
          .map(personRepository::save)
          .map(personMapper::toDTO);

    return empty();*/
  }

  private Function<UUID, Optional<PersonDTO>> findByIdAndMapperToDTO() {
    return id -> personRepository.findById(id).map(personMapper::toDTO);
  }

  public List<PersonDTO> findByFilter(PersonDTO filter) {
    return getSpecification(filter).map(s -> personRepository.findAll(s))
        .orElseGet(personRepository::findAllByEnabledTrue).stream()
        .map(personMapper::toDTO)
        .collect(Collectors.toList());
  }

  private Function<PersonDTO, Person> personToEntity() {
    return dto -> {
      if (dto.getId() != null)
        return personRepository
            .findById(dto.getId())
            .map(merge(dto))
            .orElseGet(() -> personMapper.toEntity(dto));
      return personMapper.toEntity(dto);
    };
  }

  private UnaryOperator<Person> merge(PersonDTO dto) {
    return person -> personMapper.merge(dto, person);
  }

  private UnaryOperator<Person> persist() {
    return person -> {
      try {
        return personRepository.save(person);
      } catch (Exception ex) {
        log.error(
            "Falha ao persistir a pessoa, id:{}, name:{}", person.getId(), person.getName(), ex);
        throw new PersistenceException("Falha ao persistir a pessoa", ex);
      }
    };
  }

  private UnaryOperator<Person> logPersistSuccess() {
    return person -> {
      log.info("Pessoa persistida com sucesso, id:{}, name:{}", person.getId(), person.getName());
      return person;
    };
  }

  private UnaryOperator<Person> cleanCpf() {
    return entity -> {
      entity.setCpf(documentService.cleanDocument(entity.getCpf()));
      return entity;
    };
  }
}
