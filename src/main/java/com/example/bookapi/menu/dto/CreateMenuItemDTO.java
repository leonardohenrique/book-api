package com.example.bookapi.menu.dto;

import lombok.Data;

@Data
public class CreateMenuItemDTO {

    private String name;

    private String link;

    private Integer parentId;

}
