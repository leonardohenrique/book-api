package com.example.bookapi.mother;

import com.example.bookapi.author.Author;

public class Authors {

    public static final long TOLKIEN_ID = 1L;

    public static Author.AuthorBuilder tolkien() {
        return Author.builder().id(TOLKIEN_ID).name("J.R.R. Tolkien");
    }

    public static Author.AuthorBuilder bramStoker() {
        return Author.builder().id(2L).name("Bram Stoker");
    }

}
