package com.example.bookapi.book;

import com.example.bookapi.author.Author;
import com.example.bookapi.author.AuthorRepository;
import com.example.bookapi.book.dto.CreateBookDTO;
import com.example.bookapi.book.dto.UpdateBookDTO;
import com.example.bookapi.genre.Genre;
import com.example.bookapi.genre.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        var book = bookMapper.map(createBookDTO);

        Author author = book.getAuthor();
        if (author != null && author.getId() == null) {
            author = authorRepository.save(author);
            book.setAuthor(author);
        }

        Set<Genre> genres = book.getGenres();
        Set<Genre> existingGenres = new HashSet<>();
        for (Genre genre : genres) {
            if (genre.getId() == null) {
                existingGenres.add(genreRepository.save(genre));
            } else {
                existingGenres.add(genre);
            }
        }

        book.setGenres(existingGenres);

        return bookRepository.save(book);
    }

    public Optional<Book> update(Long id, @Validated UpdateBookDTO updateBookDTO) {
        return bookRepository.findById(id).map((book) -> {
            bookMapper.map(updateBookDTO, book);
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
