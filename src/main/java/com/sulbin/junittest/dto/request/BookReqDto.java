package com.sulbin.junittest.dto.request;

import com.sulbin.junittest.domain.Book;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter //Controller에서 Setter가 호출되면서  Dto에 값이 채워짐.
public class BookReqDto {

    @NotBlank
    @Size(min=1, max=50)
    private String title;

    @NotBlank
    @Size(min=2, max=50)
    private String author;

    public Book toEntity(){
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
