package com.sulbin.junittest.service;


import com.sulbin.junittest.domain.Book;
import com.sulbin.junittest.dto.request.BookReqDto;
import com.sulbin.junittest.dto.response.BookListResDto;
import com.sulbin.junittest.dto.response.BookResDto;
import com.sulbin.junittest.repository.BookRepository;
import com.sulbin.junittest.util.MailSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void bookSave_test(){ // 책 등록 테스트
        //given
        BookReqDto bookReqDto = new BookReqDto();
        bookReqDto.setTitle("sulbinTest");
        bookReqDto.setAuthor("wook");

        //stub(가설)
        when(bookRepository.save(any())).thenReturn(bookReqDto.toEntity());
        when(mailSender.send()).thenReturn(true);

        //when
        BookResDto bookResDto = bookService.bookSave(bookReqDto);

        //then
        assertThat(bookResDto.getTitle()).isEqualTo(bookReqDto.getTitle());
        assertThat(bookResDto.getAuthor()).isEqualTo(bookReqDto.getAuthor());

    }

    @Test
    public void bookSelect(){ //책 전체보기
        //given

        //stub
        List<Book> books = new ArrayList<>();
        books.add(Book.builder().id(1L).title("oneTitle").author("wook").build());
        books.add(Book.builder().id(2L).title("twoTitle").author("sulbin").build());

        when(bookRepository.findAll()).thenReturn(books);

        //when
        BookListResDto bookListResDto = bookService.bookList();

        //then
        assertThat(bookListResDto.getDatas().get(0).getTitle()).isEqualTo("oneTitle");
        assertThat(bookListResDto.getDatas().get(1).getTitle()).isEqualTo("twoTitle");
   }

    @DisplayName("책 한건 보기")
    @Test
    public void bookDetails(){
        //given
        Long id = 1L;

        //stub
        Book book = Book.builder()
                .id(id)
                .title("서비스테스트_책한건보기_타이틀")
                .author("설빈")
                .build();
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        //when
        BookResDto bookResDto = bookService.bookOne(id);

        //then
        assertThat(bookResDto.getTitle()).isEqualTo(book.getTitle());
    }

    @Test //책 수정하기
    public void bookUpdate(){
        //given (파라미터로 받은 변수)
        Long id = 1L;
        BookReqDto bookReqDto = new BookReqDto();
        bookReqDto.setTitle("서비스테스트_책수정(전)_타이틀");
        bookReqDto.setAuthor("설빈");

        //stub
        Book book = Book.builder()
                .id(1L)
                .title("서비스테스트_책수정(후)_타이틀")
                .author("설빈")
                .build();
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        //when
        BookResDto bookResDto = bookService.bookUpdate(id,bookReqDto);

        //then
        assertThat(bookResDto.getTitle()).isEqualTo(bookReqDto.getTitle());

    }

}











