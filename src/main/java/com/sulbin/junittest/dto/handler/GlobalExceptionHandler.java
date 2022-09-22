package com.sulbin.junittest.dto.handler;

import com.sulbin.junittest.dto.response.CMResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> apiException(RuntimeException e){
        System.out.println("sdsdsaddasd");
//        return ResponseEntity.status(123).body(CMResDto.builder().code(-1).msg(e.getMessage()).body(e.getMessage()).build());
        return new ResponseEntity<>(CMResDto.builder().code(-1).msg(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        //이거 수정해 보기
    }

}
