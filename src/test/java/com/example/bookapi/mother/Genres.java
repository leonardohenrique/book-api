package com.example.bookapi.mother;

import com.example.bookapi.genre.Genre;

public class Genres {

    public static final long FANTASY_ID = 1L;
    public static final long HORROR_ID = 3L;

    public static Genre.GenreBuilder fantasy() {
        return Genre.builder().id(FANTASY_ID).name("Fantasy");
    }

    public static Genre.GenreBuilder horror() {
        return Genre.builder().id(HORROR_ID).name("Horror");
    }
}
