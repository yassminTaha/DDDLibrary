package com.example.DDDLibrary.Library.Catalogue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTests {
    private Book book;

    @BeforeEach
    void setUp() throws ParseException {
        book = new Book("12345","Harry Potter & the philosopher's stone","J.K Rolling");
    }

    @Test
    void checkBook()
    {
        assertEquals("12345", book.getISBN());
        assertEquals("Harry Potter & the philosopher's stone", book.getTitle());
        assertEquals("J.K Rolling", book.getAuthor());
    }
}

