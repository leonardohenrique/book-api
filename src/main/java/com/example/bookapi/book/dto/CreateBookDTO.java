package com.example.bookapi.book.dto;

import lombok.Data;

import java.util.Set;

@Data

public class CreateBookDTO {
    private String title;
    private Long authorId;
    private Set<Long> genreIds;
    private int year;
}
