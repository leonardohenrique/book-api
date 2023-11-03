package com.example.bookapi.book;

import com.example.bookapi.author.AuthorRepository;
import com.example.bookapi.book.dto.CreateBookDTO;
import com.example.bookapi.genre.GenreRepository;
import com.example.bookapi.mother.Authors;
import com.example.bookapi.mother.Genres;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.example.bookapi.mother.Authors.TOLKIEN_ID;
import static com.example.bookapi.mother.Genres.FANTASY_ID;
import static com.example.bookapi.mother.Genres.HORROR_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookMapperTest {

    @Mock
    AuthorRepository authorRepository;

    @Mock
    GenreRepository genreRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Test
    void testMapCreateBookDTO() {
        BookMapper bookMapper = new BookMapper(authorRepository, genreRepository, modelMapper);

        var author = Authors.tolkien().build();
        var genres = List.of(Genres.fantasy().build(), Genres.horror().build());

        when(authorRepository.findById(TOLKIEN_ID)).thenReturn(Optional.of(author));
        when(genreRepository.findAllById(List.of(FANTASY_ID, HORROR_ID))).thenReturn(genres);

        CreateBookDTO createBookDTO = new CreateBookDTO();
        createBookDTO.setTitle("The Hobbit");
        createBookDTO.setAuthorId(TOLKIEN_ID);
        createBookDTO.setGenreIds(List.of(FANTASY_ID, HORROR_ID));
        createBookDTO.setYear(1937);

        var book = bookMapper.map(createBookDTO);

        assertThat(book.getId()).isNull();
        assertThat(book.getTitle()).isEqualTo("The Hobbit");

        System.out.println(book);
    }
}