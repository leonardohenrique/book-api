package com.example.bookapi.author;

import com.example.bookapi.author.dto.CreateAuthorDTO;
import com.example.bookapi.author.dto.UpdateAuthorDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorMapper {

    private final ModelMapper modelMapper;

    public Author map(CreateAuthorDTO createAuthorDTO) {
        Author author = new Author();
        author.setName(createAuthorDTO.getName());
        return author;
    }

    public void map(UpdateAuthorDTO updateAuthorDTO, Author author) {
        author.setName(updateAuthorDTO.getName());
    }

}
