package com.example.bookapi.book.dto;

import com.example.bookapi.author.Author;
import com.example.bookapi.genre.Genre;
import lombok.Data;

import java.util.Set;

@Data

public class CreateBookDTO {
    private String title;
    private Author author;
    private Set<Genre> genres;
    private int year;
}
