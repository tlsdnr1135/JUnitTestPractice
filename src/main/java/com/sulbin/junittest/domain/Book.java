package com.sulbin.junittest.domain;

import com.sulbin.junittest.dto.response.BookResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50,nullable = false)
    private String title;
    @Column(length = 50,nullable = false)
    private String author;

    @Builder
    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    //책 수정하기
    public void update(String title, String author){
        this.title = title;
        this.author = author;
    }

    public BookResDto toResDto(){
        return BookResDto.builder()
                .id(id)
                .title(title)
                .author(author)
                .build();
    }

}
