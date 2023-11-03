package com.example.bookapi.book;

import com.example.bookapi.author.AuthorRepository;
import com.example.bookapi.book.dto.CreateBookDTO;
import com.example.bookapi.book.dto.UpdateBookDTO;
import com.example.bookapi.genre.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public Book map(CreateBookDTO createBookDTO) {
        // var book = modelMapper.map(createBookDTO, Book.class);
        var book = new Book();
        book.setTitle(createBookDTO.getTitle());
        book.setYear(createBookDTO.getYear());

        if (createBookDTO.getAuthorId() != null) {
            book.setAuthor(authorRepository.findById(createBookDTO.getAuthorId()).orElseThrow(IllegalArgumentException::new));
        }

        if (book.getGenres() != null) {
            book.getGenres().clear();
        }

        book.setGenres(genreRepository.findAllById(createBookDTO.getGenreIds()));

        return book;

    }

    public void map(UpdateBookDTO updateBookDTO, Book book) {
        modelMapper.map(updateBookDTO, book);

        if (book.getGenres() != null) {
            book.getGenres().clear();
        }

        book.setAuthor(authorRepository.findById(updateBookDTO.getAuthorId()).orElseThrow(IllegalArgumentException::new));
        book.setGenres(genreRepository.findAllById(updateBookDTO.getGenreIds()));
    }

}
