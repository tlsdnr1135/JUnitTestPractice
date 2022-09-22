package com.sulbin.junittest.dto.response;

import com.sulbin.junittest.domain.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BookResDto {
    private Long id;
    private String title;
    private String author;

    @Builder
    public BookResDto(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

//    public BookResDto toDto(Book book){
//        this.id = book.getId();
//        this.title = book.getTitle();
//        this.author = book.getAuthor();
//        return this;
//    }

}
