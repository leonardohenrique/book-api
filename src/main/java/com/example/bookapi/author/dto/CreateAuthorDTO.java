package com.example.bookapi.author.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAuthorDTO {

    @NotBlank
    @Size(max = 255)
    private String name;

}
