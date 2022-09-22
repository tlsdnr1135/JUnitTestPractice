package com.sulbin.junittest.controller;

import com.sulbin.junittest.dto.request.BookReqDto;
import com.sulbin.junittest.dto.response.BookResDto;
import com.sulbin.junittest.dto.response.CMResDto;
import com.sulbin.junittest.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    // 1. 책등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBookBook(@RequestBody @Valid BookReqDto bookReqDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()){
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            System.out.println("-----------------------------");
            System.out.println(bindingResult.hasErrors());
            System.out.println("-----------------------------");
            throw new RuntimeException(errorMap.toString());
        }
        System.out.println("밖");
        BookResDto bookResDto = bookService.bookSave(bookReqDto);
        CMResDto<?> cmResDto = CMResDto.builder().code(1).msg("컨트롤러 테스트 CMR").body(bookResDto).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(cmResDto); //created
    }

    // 2. 책 목록보기
    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList(){

        return ResponseEntity.status(HttpStatus.OK).body(CMResDto.builder().code(1).msg("글 목록 불러오기 성공").body(bookService.bookList()).build());
    }

    // 3. 책 한건보기
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookDetails(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(CMResDto.builder().code(1).msg("글 상세보기 성공").body(bookService.bookOne(id)).build());
    }

    // 4. 책 삭제하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        bookService.bookDelete(id);
        return ResponseEntity.status(HttpStatus.OK).body(CMResDto.builder().code(1).msg("글 삭제 성공").build());
    }

    // 5. 책 수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid BookReqDto bookReqDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()){
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }

        return ResponseEntity.status(HttpStatus.OK).body(CMResDto.builder().code(1).msg("글 수정 성공").body(bookService.bookUpdate(id,bookReqDto)).build());
    }
}