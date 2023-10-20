package com.example.bookapi.book;

import com.example.bookapi.book.dto.CreateBookDTO;
import com.example.bookapi.book.dto.UpdateBookDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMapper {

    private final ModelMapper modelMapper;

    public Book map(CreateBookDTO createBookDTO) {
        return modelMapper.map(createBookDTO, Book.class);
    }

    public void map(UpdateBookDTO bookDTO, Book book) {
        modelMapper.map(bookDTO, book);
    }

}
