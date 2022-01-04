package com.example.DDDLibrary.Library.Lending.Book.Domain;

import com.example.DDDLibrary.Library.Catalogue.BookType;
import com.example.DDDLibrary.Library.Lending.LibraryBranch.Domain.LibraryBranchId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AvailableBookTests {
    private AvailableBook availableBook;
    private UUID libraryBranchId;
    private UUID bookId;
    @BeforeEach
    void setUp() throws ParseException {
        libraryBranchId = UUID.randomUUID();
        bookId = UUID.randomUUID();
        availableBook = new AvailableBook(bookId, BookType.Restricted,new LibraryBranchId(libraryBranchId));
    }

    @Test
    void checkAvailableBook()
    {
        assertEquals(bookId, availableBook.getBookId());
        assertEquals(libraryBranchId, availableBook.getLibraryBranchId());
        assertEquals(true, availableBook.isRestricted());
    }
}

