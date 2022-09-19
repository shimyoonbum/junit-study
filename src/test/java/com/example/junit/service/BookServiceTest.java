package com.example.junit.service;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.junit.domain.Book;
import com.example.junit.domain.BookRepository;
import com.example.junit.util.MailSender;
import com.example.junit.web.dto.request.BookSaveReqDto;
import com.example.junit.web.dto.response.BookListRespDto;
import com.example.junit.web.dto.response.BookRespDto;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    //3개(서비스, 레포지토리, 메일 Util 을 가짜환경 메모리에 올림)
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    // 문제점 -> 서비스만 테스트하고 싶은데, 레포지토리 레이어가 함께 테스트 된다는 점!! 고로 레포지토리는 Mock(가짜 객체) 로 테스트한다.
    @Test
    public void 책등록하기_테스트() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit강의");
        dto.setAuthor("메타코딩");

        // stub (가설)
        lenient().when(bookRepository.save(any())).thenReturn(dto.toEntity());
        lenient().when(mailSender.send()).thenReturn(true);

        // when
        BookRespDto bookRespDto = bookService.책등록하기(dto);

        // then
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
    }

    @Test
    public void 책목록보기_테스트() {
        // given(파라메터로 들어올 데이터)

        // stub(가설)
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit강의", "메타코딩"));
        books.add(new Book(2L, "spring강의", "겟인데어"));
        lenient().when(bookRepository.findAll()).thenReturn(books);

        // when(실행)
        BookListRespDto bookListRespDto = bookService.책목록보기();

        // print
        // dtos.stream().forEach((dto) -> {
        //     System.out.println("============= 테스트");
        //     System.out.println(dto.getId());
        //     System.out.println(dto.getTitle());

        // });

        // then(검증)
        assertThat(bookListRespDto.getItems().get(0).getTitle()).isEqualTo("junit강의");
        assertThat(bookListRespDto.getItems().get(0).getAuthor()).isEqualTo("메타코딩");
        assertThat(bookListRespDto.getItems().get(1).getTitle()).isEqualTo("spring강의");
        assertThat(bookListRespDto.getItems().get(1).getAuthor()).isEqualTo("겟인데어");
    }

    @Test
    public void 책한건보기_테스트() {
        // given
        Long id = 1L;

        // stub
        Book book = new Book(1L, "junit강의", "메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        lenient().when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.책한건보기(id);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    public void 책수정하기_테스트() {
        // given
        Long id = 1L;
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("spring강의"); // spring강의
        dto.setAuthor("겟인데어"); // 겟인데어

        // stub
        Book book = new Book(1L, "junit강의", "메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        lenient().when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.책수정하기(id, dto);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());

    }
}
