package com.sulbin.junittest.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BookListResDto {

    List<BookResDto>  datas;

    @Builder
    public BookListResDto(List<BookResDto> bookLists) {
        this.datas = bookLists;
    }
}
