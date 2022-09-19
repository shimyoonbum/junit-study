package com.example.junit.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BookRespDto {
    private Long id;
    private String title;
    private String author;

    // public BookRespDto toDto(Book bookPS) {
    //     this.id = bookPS.getId();
    //     this.title = bookPS.getTitle();
    //     this.author = bookPS.getAuthor();
    //     return this;
    // }

    @Builder
    public BookRespDto(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}