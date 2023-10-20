package com.example.bookapi.author;

import com.example.bookapi.author.dto.CreateAuthorDTO;
import com.example.bookapi.author.dto.UpdateAuthorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    public Author create(@Validated CreateAuthorDTO createAuthorDTO) {
        Author author = authorMapper.map(createAuthorDTO);
        return authorRepository.save(author);
    }

    public Optional<Author> update(Long id, @Validated UpdateAuthorDTO updateAuthorDTO) {
        return authorRepository.findById(id).map((author) -> {
            authorMapper.map(updateAuthorDTO, author);
            return authorRepository.save(author);
        });
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return authorRepository.existsById(id);
    }


}
