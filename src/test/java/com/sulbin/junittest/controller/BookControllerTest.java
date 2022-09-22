package com.sulbin.junittest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.sulbin.junittest.dto.request.BookReqDto;
import com.sulbin.junittest.service.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

//통합 테스트(C,S,R)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //통합테스트
public class BookControllerTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static ObjectMapper objectMapper;
    private static HttpHeaders httpHeaders;

    @BeforeAll
    public static void init(){
        objectMapper = new ObjectMapper();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void saveBook_test() throws Exception{
        //given
        BookReqDto bookReqDto = new BookReqDto();
        bookReqDto.setTitle("신욱_타이틀");
        bookReqDto.setAuthor("신욱_작성자");

        String body = objectMapper.writeValueAsString(bookReqDto); //json으로 바꾸기

        //when
        HttpEntity<String> httpEntity = new HttpEntity<>(body,httpHeaders);

        ResponseEntity<String> response = testRestTemplate.exchange("/api/v1/book", HttpMethod.POST, httpEntity, String.class);

        //then
        DocumentContext documentContext = JsonPath.parse(response.getBody());

        String title = documentContext.read("$.body.title");
        String author = documentContext.read("$.body.author");

        assertThat(title).isEqualTo("신욱_타이틀");
        assertThat(author).isEqualTo(bookReqDto.getAuthor());
    }

}
