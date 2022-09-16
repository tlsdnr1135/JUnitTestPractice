package com.sulbin.junittest.repository;

import com.sulbin.junittest.domain.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest //DB와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    
    //@BeforeAll //테스트 시작전에 한번만 실행
    @BeforeEach //각각의 테스트 시작전에 한번씩 실행
    public void Data(){
        String title  = "junit";
        String author = "sulbin";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    }//이 함수가 실행된 후 하나의 함수까지만 트랜잭션이 적용됨.
    

    // 1.책 등록
    @Test
    public void BookSave_test(){ //책 등록
        //given (데이터 준비)
        String title = "junit5";
        String author = "wook";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        //when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        //then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());

    }

    // 2.책 목록보기
    @Test
    public void BookSelect_test(){ //책 전체조회
        //given
        String title = "junit";
        String author = "sulbin";
        //when
        List<Book> booksPS = bookRepository.findAll();

        System.out.println(booksPS.get(0).getTitle());

        //then
        assertEquals(title, booksPS.get(0).getTitle());
        assertEquals(author, booksPS.get(0).getAuthor());

    }

    // 3. 책 한건보기
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void BookDetails_test(){
        //given
        String title = "junit";
        String author = "sulbin";

        //when
        Book bookPS = bookRepository.findById(1L).get();

        //then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());

    }

    // 4. 책 삭제
    @Test
    public void BookDelete_test(){
        //given
        Long id = 1L;

        //when
        bookRepository.deleteById(id);

        //then
        Optional<Book> bookPS = bookRepository.findById(id);
        assertFalse(bookPS.isPresent());
    }

    // 5. 책 수정
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void BookUpdate_test(){
        //given
        Long id = 1L;
        String title = "book_update_title";
        String author = "book_update_author";
        Book book = Book.builder()
                .id(id)
                .title(title)
                .author(author)
                .build();
        bookRepository.findAll().stream()
                .forEach((b) -> {
                    System.out.println(b.getId());
                    System.out.println(b.getTitle());
                    System.out.println(b.getAuthor());
                    System.out.println("===================");
                });

        //when
        Book bookPS = bookRepository.save(book);

        bookRepository.findAll().stream()
                .forEach((b) -> {
                    System.out.println(b.getId());
                    System.out.println(b.getTitle());
                    System.out.println(b.getAuthor());
                    System.out.println("===================");
                });
        System.out.println(bookPS.getId());
        System.out.println(bookPS.getTitle());
        System.out.println(bookPS.getAuthor());
        System.out.println("===================");
        //then
        assertEquals(id,bookPS.getId());
        assertEquals(title,bookPS.getTitle());
        assertEquals(author,bookPS.getAuthor());

    }

}
