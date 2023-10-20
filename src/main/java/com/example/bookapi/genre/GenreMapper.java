package com.example.bookapi.genre;

import com.example.bookapi.genre.dto.CreateGenreDTO;
import com.example.bookapi.genre.dto.UpdateGenreDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreMapper {

    private final ModelMapper modelMapper;

    public Genre map(CreateGenreDTO createGenreDTO) {
        Genre genre = new Genre();
        genre.setName(createGenreDTO.getName());
        return genre;
    }

    public void map(UpdateGenreDTO updateGenreDTO, Genre genre) {
        genre.setName(updateGenreDTO.getName());
    }

}
