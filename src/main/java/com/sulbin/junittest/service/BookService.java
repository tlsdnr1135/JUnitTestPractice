package com.sulbin.junittest.service;

import com.sulbin.junittest.domain.Book;
import com.sulbin.junittest.dto.request.BookReqDto;
import com.sulbin.junittest.dto.response.BookListResDto;
import com.sulbin.junittest.dto.response.BookResDto;
import com.sulbin.junittest.repository.BookRepository;
import com.sulbin.junittest.util.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;


    // 1. 책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResDto bookSave(BookReqDto bookReqDto){
        System.out.println("------------------------");
        System.out.println(bookReqDto.getAuthor());
        System.out.println("------------------------");
        Book bookPS = bookRepository.save(bookReqDto.toEntity());
        if(bookPS != null){
            if(!mailSender.send()){
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
        }
        return bookPS.toResDto();
    }

    // 2. 책 목록 보기
    public BookListResDto bookList(){
        List<BookResDto> bookResDtos = bookRepository.findAll().stream()
                .map(Book::toResDto)
                .collect(Collectors.toList());
        BookListResDto bookListResDto = BookListResDto.builder().bookLists(bookResDtos).build();
        return bookListResDto;
    }

    // 3. 책 상세 보기
    public BookResDto bookOne(Long id){
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()){
            return bookOP.get().toResDto();
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

    // 4. 책 삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void bookDelete(Long id){
        bookRepository.deleteById(id);
    }

    // 5. 책 수정하기기
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResDto bookUpdate(Long id,BookReqDto bookReqDto){
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()){
            Book bookPS = bookOP.get();
            bookPS.update(bookReqDto.getTitle(), bookReqDto.getAuthor());
            return bookPS.toResDto();
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }

    }
}
