package com.spring.springbootcrud;

import static java.util.UUID.fromString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.spring.springbootcrud.domain.dto.PersonDTO;
import com.spring.springbootcrud.domain.entity.Person;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseUnitTest {

  protected static final UUID ID = fromString("cabcc0d8-55bd-45a0-a409-1cb3ee292d3a");
  protected static final String NAME = "Usuário de Teste";
  protected static final String INVALID_CPF = "123.456.789-10";
  protected static final String VALID_CPF = "12345678910";
  protected static final String ADDRESS =
      "Rua teste, Bairro Java, num: 53 - Apto 103, Joinville - SC";
  protected LocalDate bornDate;
  protected PersonDTO personDTO;
  protected PersonDTO expectedPersonDTO;
  protected Person person;

  @Before
  public void setup() {
    bornDate = LocalDate.of(1989, 11, 1);
    personDTO = buildPersonDTO(INVALID_CPF);
    expectedPersonDTO = buildPersonDTO(VALID_CPF);
    person = buildPerson();
  }

  protected void assertAllAttributesOfPersonDTO(PersonDTO actualPersonDTO) {
    assertThat(actualPersonDTO.getId(), equalTo(expectedPersonDTO.getId()));
    assertThat(actualPersonDTO.getName(), equalTo(expectedPersonDTO.getName()));
    assertThat(actualPersonDTO.getCpf(), equalTo(expectedPersonDTO.getCpf()));
    assertThat(actualPersonDTO.getBornDate(), equalTo(expectedPersonDTO.getBornDate()));
    assertThat(actualPersonDTO.getAddress(), equalTo(expectedPersonDTO.getAddress()));
    assertThat(actualPersonDTO, equalTo(expectedPersonDTO));
  }

  protected void assertAllAttributesOfPerson(Person actualPerson) {
    assertThat(actualPerson.getId(), equalTo(person.getId()));
    assertThat(actualPerson.getName(), equalTo(person.getName()));
    assertThat(actualPerson.getCpf(), equalTo(person.getCpf()));
    assertThat(actualPerson.getBornDate(), equalTo(person.getBornDate()));
    assertThat(actualPerson.getAddress(), equalTo(person.getAddress()));
    assertThat(actualPerson, equalTo(person));
  }

  protected PersonDTO buildPersonDTO(String cpf) {
    return buildPersonDTO(cpf, ID);
  }

  protected PersonDTO buildPersonDTO(String cpf, UUID id) {
    return PersonDTO.builder()
        .id(id)
        .name(NAME)
        .cpf(cpf)
        .bornDate(bornDate)
        .address(ADDRESS)
        .build();
  }

  protected Person buildPerson() {
    return Person.builder()
        .id(ID)
        .name(NAME)
        .cpf(VALID_CPF)
        .bornDate(bornDate)
        .address(ADDRESS)
        .build();
  }

  protected <T extends Annotation> T getAnnotation(Method method, Class<T> clazz) {
    return method.getAnnotation(clazz);
  }

  protected Method getMethod(Class clazz, String methodName) {
    return Arrays.stream(clazz.getMethods())
        .filter(m -> m.getName().equals(methodName))
        .findFirst()
        .orElseThrow(RuntimeException::new);
  }
}
