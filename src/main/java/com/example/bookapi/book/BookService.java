package com.example.bookapi.book;

import com.example.bookapi.author.AuthorRepository;
import com.example.bookapi.book.dto.CreateBookDTO;
import com.example.bookapi.book.dto.UpdateBookDTO;
import com.example.bookapi.genre.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookMapper bookMapper;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book create(@Validated CreateBookDTO createBookDTO) {
        log.info("Creating book with DTO {}", createBookDTO);
        var book = bookMapper.map(createBookDTO);
        log.info("Saving book {}", book);
        return bookRepository.save(book);
    }


    public Optional<Book> update(Long id, @Validated UpdateBookDTO updateBookDTO) {
        log.info("Updating book with DTO {}", updateBookDTO);

        return bookRepository.findById(id).map((book) -> {
            bookMapper.map(updateBookDTO, book);
            log.info("Saving book {}", book);
            return bookRepository.save(book);
        });
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }


}
