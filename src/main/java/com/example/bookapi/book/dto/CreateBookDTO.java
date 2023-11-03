package com.example.bookapi.book.dto;

import lombok.Data;

import java.util.List;

@Data

public class CreateBookDTO {
    private String title;
    private Long authorId;
    private List<Long> genreIds;
    private int year;
}
