package com.spring.springbootcrud.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.spring.springbootcrud.BaseUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class DocumentServiceTest extends BaseUnitTest {

  @InjectMocks private DocumentService documentService;

  @Test
  public void mustOnlyNumberWhenHasAnyCharactersInDocument() {
    final String actual = documentService.cleanDocument(INVALID_CPF);

    assertThat(actual, equalTo(VALID_CPF));
  }

  @Test
  public void mustOnlyNumberWhenHasOnlyNumbersInDocument() {
    final String actual = documentService.cleanDocument(VALID_CPF);

    assertThat(actual, equalTo(VALID_CPF));
  }
}
