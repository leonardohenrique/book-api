package com.example.bookapi.genre.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateGenreDTO {

    @NotBlank
    @Size(max = 255)
    private String name;

}
