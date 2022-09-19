package com.example.junit.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.junit.domain.Book;
import com.example.junit.domain.BookRepository;
import com.example.junit.web.dto.request.BookSaveReqDto;
import com.example.junit.web.dto.response.BookListRespDto;
import com.example.junit.web.dto.response.BookRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    // 1. 책등록
    @Transactional(rollbackOn = RuntimeException.class)
    public BookRespDto 책등록하기(BookSaveReqDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        //return new BookRespDto().toDto(bookPS);
        return bookPS.toDto();
    }   

    // 2. 책목록보기
    public BookListRespDto 책목록보기() {
        
        List<BookRespDto> dtos = bookRepository.findAll().stream()
                //.map((bookPS) -> bookPS.toDto())
                 .map(Book::toDto)
                // .map(new BookRespDto()::toDto)
                .collect(Collectors.toList());
        
        BookListRespDto bookListRespDto = BookListRespDto.builder().bookList(dtos).build();
        return bookListRespDto;
    }

    // 3. 책한건보기
    public BookRespDto 책한건보기(Long id) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) { // 찾았다면
            //return new BookRespDto().toDto(bookOP.get());
            Book bookPS = bookOP.get();
            return bookPS.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

    // 4. 책삭제하기
    @Transactional(rollbackOn = RuntimeException.class)
    public void 책삭제하기(Long id) { // 4
        bookRepository.deleteById(id); // 1,2,3
    }

    // 5. 책수정하기
    @Transactional(rollbackOn = RuntimeException.class)
    public BookRespDto 책수정하기(Long id, BookSaveReqDto dto) { // id, title, author
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
            return bookPS.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    } // 메서드 종료시에 더티체킹(flush)으로 update 됩니다.
}
