package com.example.bookapi.author;

import com.example.bookapi.author.dto.CreateAuthorDTO;
import com.example.bookapi.author.dto.UpdateAuthorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<Author> getAll() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable Long id) {
        return authorService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found with id " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author create(@RequestBody @Validated CreateAuthorDTO createAuthorDTO) {
        return authorService.create(createAuthorDTO);
    }

    @PutMapping("/{id}")
    public Author update(@PathVariable Long id, @Validated @RequestBody UpdateAuthorDTO updateAuthorDTO) {
        return authorService.update(id, updateAuthorDTO).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found with id " + id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!authorService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found with id " + id);
        }

        authorService.deleteById(id);
    }
}
