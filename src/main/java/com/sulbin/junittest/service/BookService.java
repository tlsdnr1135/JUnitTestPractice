package com.sulbin.junittest.service;

import com.sulbin.junittest.domain.Book;
import com.sulbin.junittest.dto.BookReqDto;
import com.sulbin.junittest.dto.BookResDto;
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
        Book bookPS = bookRepository.save(bookReqDto.toEntity());
        if(bookPS != null){
            if(!mailSender.send()){
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
        }
        return new BookResDto().toDto(bookPS);
    }

    // 2. 책 목록 보기
    public List<BookResDto> bookList(){
        return bookRepository.findAll().stream().map(new BookResDto()::toDto).collect(Collectors.toList());
    }

    // 3. 책 상세 보기
    public BookResDto bookOne(Long id){
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()){
            return new BookResDto().toDto(bookOP.get());
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
    public void bookUpdate(Long id,BookReqDto bookReqDto){
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()){
            Book bookPS = bookOP.get();
            bookPS.update(bookReqDto.getTitle(), bookReqDto.getAuthor());
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
        bookRepository.deleteById(id);
    }
}
