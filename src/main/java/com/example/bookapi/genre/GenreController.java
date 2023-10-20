package com.example.bookapi.genre;

import com.example.bookapi.genre.dto.CreateGenreDTO;
import com.example.bookapi.genre.dto.UpdateGenreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<Genre> getAll() {
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public Genre getById(@PathVariable Long id) {
        return genreService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found with id " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Genre create(@RequestBody @Validated CreateGenreDTO createGenreDTO) {
        return genreService.create(createGenreDTO);
    }

    @PutMapping("/{id}")
    public Genre update(@PathVariable Long id, @Validated @RequestBody UpdateGenreDTO updateGenreDTO) {
        return genreService.update(id, updateGenreDTO).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found with id " + id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!genreService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found with id " + id);
        }

        genreService.deleteById(id);
    }
}
