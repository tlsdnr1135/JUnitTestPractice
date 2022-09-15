package com.sulbin.junittest.service;


import com.sulbin.junittest.dto.BookReqDto;
import com.sulbin.junittest.dto.BookResDto;
import com.sulbin.junittest.repository.BookRepository;
import com.sulbin.junittest.util.MailSender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static  org.assertj.core.api.WithAssertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    public void bookSave_test(){
        //given
        BookReqDto bookReqDto = new BookReqDto();
        bookReqDto.setTitle("sulbinTest");
        bookReqDto.setAuthor("wook");

        //stub(가설)
        when(bookRepository.save(any())).thenReturn(bookReqDto.toEntity());
//        when(mailSender.send()).thenReturn(true);

        //when
        BookResDto bookResDto = bookService.bookSave(bookReqDto);

        //then
        assertThat(bookReqDto.getTitle()).isEqualTo(bookResDto.getTitle());
        assertThat(bookReqDto.getAuthor()).isEqualTo(bookResDto.getAuthor());

    }


}











