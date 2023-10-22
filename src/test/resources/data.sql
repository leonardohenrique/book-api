INSERT INTO genres (name)
VALUES ('Fantasy'),
       ('Classics'),
       ('Horror');

INSERT INTO authors (name)
VALUES ('J.R.R. Tolkien'),
       ('Bram Stoker');

INSERT INTO books (title, author_id, year)
VALUES ('The Hobbit', 1, 1937),
       ('The Lord of the Rings', 1, 1954),
       ('Dracula', 2, 1897);

INSERT INTO book_genres (book_id, genre_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (3, 3),
       (3, 2);