package com.spring.springbootcrud.domain.repository;

import static com.spring.springbootcrud.domain.repository.PersonRepository.getSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.spring.springbootcrud.BaseUnitTest;
import com.spring.springbootcrud.domain.dto.PersonDTO;
import com.spring.springbootcrud.domain.entity.Person;
import java.util.Optional;
import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;

public class PersonRepositoryTest extends BaseUnitTest {

  @Test
  public void mustGetSpecificationWhenHasCompleteFilter() {
    final Optional<Specification<Person>> specification = getSpecification(expectedPersonDTO);

    assertThat(specification.isPresent(), is(true));
  }

  @Test
  public void mustEmptySpecificationWhenHasNullFilter() {
    final Optional<Specification<Person>> specification = getSpecification(null);

    assertThat(specification.isPresent(), is(false));
  }

  @Test
  public void mustEmptySpecificationWhenHasEmptyFilter() {
    final Optional<Specification<Person>> specification =
        getSpecification(PersonDTO.builder().build());

    assertThat(specification.isPresent(), is(false));
  }

  @Test
  public void mustGetSpecificationWhenTheFilterHasOnlyCpf() {
    final Optional<Specification<Person>> specification =
        getSpecification(PersonDTO.builder().cpf(VALID_CPF).build());

    assertThat(specification.isPresent(), is(true));
  }

  @Test
  public void mustGetSpecificationWhenTheFilterHasOnlyName() {
    final Optional<Specification<Person>> specification =
        getSpecification(PersonDTO.builder().name(NAME).build());

    assertThat(specification.isPresent(), is(true));
  }

  @Test
  public void mustGetSpecificationWhenTheFilterHasOnlyAddress() {
    final Optional<Specification<Person>> specification =
        getSpecification(PersonDTO.builder().address(ADDRESS).build());

    assertThat(specification.isPresent(), is(true));
  }

  @Test
  public void mustHasCpfWhenHasCpfString() {
    final Specification<Person> hasCpf = PersonRepository.hasCpf(VALID_CPF);

    assertThat(hasCpf, notNullValue());
  }

  @Test
  public void mustNameContainsWhenHasNameString() {
    final Specification<Person> nameContains = PersonRepository.nameContains(NAME);

    assertThat(nameContains, notNullValue());
  }

  @Test
  public void mustAddressContainsWhenHasAddressString() {
    final Specification<Person> addressContains = PersonRepository.addressContains(ADDRESS);

    assertThat(addressContains, notNullValue());
  }
}
