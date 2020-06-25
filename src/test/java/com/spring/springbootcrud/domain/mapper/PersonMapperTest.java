package com.spring.springbootcrud.domain.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.spring.springbootcrud.BaseUnitTest;
import com.spring.springbootcrud.domain.dto.PersonDTO;
import com.spring.springbootcrud.domain.entity.Person;
import org.junit.Test;
import org.mockito.InjectMocks;

public class PersonMapperTest extends BaseUnitTest {

  @InjectMocks private PersonMapper personMapper;

  @Test
  public void mustPersonWhenHasPersonDTO() {
    final Person actualPerson = personMapper.toEntity(expectedPersonDTO);

    assertThat(actualPerson, equalTo(person));
  }

  @Test
  public void mustPersonDTOWhenHasPerson() {
    final PersonDTO actualPersonDTO = personMapper.toDTO(person);

    assertThat(actualPersonDTO, equalTo(expectedPersonDTO));
  }

  @Test
  public void mustNotMergeWhenHasEmptyDto() {
    final Person actualPerson = personMapper.merge(PersonDTO.builder().build(), person);

    assertAllAttributesOfPerson(actualPerson);
  }

  @Test
  public void mustMergeWhenHasDtoIsValid() {
    final Person actualPerson = personMapper.merge(expectedPersonDTO, Person.builder().build());

    assertThat(actualPerson.getName(), equalTo(NAME));
    assertThat(actualPerson.getCpf(), equalTo(VALID_CPF));
    assertThat(actualPerson.getAddress(), equalTo(ADDRESS));
    assertThat(actualPerson.getBornDate(), equalTo(bornDate));
  }
}
