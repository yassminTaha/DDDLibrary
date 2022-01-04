package com.example.DDDLibrary.Library.Catalogue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class BookInstanceTests {
    private BookInstance booInstance;

    @BeforeEach
    void setUp() throws ParseException {
        Book book=  new Book("12345","Harry Potter & the philosopher's stone","J.K Rolling");
        booInstance = new BookInstance(book,BookType.Restricted);
    }

    @Test
    void checkBook()
    {
        assertEquals(BookType.Restricted, booInstance);
    }


}

