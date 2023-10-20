package com.example.bookapi.genre;

import com.example.bookapi.genre.dto.CreateGenreDTO;
import com.example.bookapi.genre.dto.UpdateGenreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    public Genre create(@Validated CreateGenreDTO createGenreDTO) {
        Genre genre = genreMapper.map(createGenreDTO);
        return genreRepository.save(genre);
    }

    public Optional<Genre> update(Long id, @Validated UpdateGenreDTO updateGenreDTO) {
        return genreRepository.findById(id).map((genre) -> {
            genreMapper.map(updateGenreDTO, genre);
            return genreRepository.save(genre);
        });
    }

    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return genreRepository.existsById(id);
    }


}
