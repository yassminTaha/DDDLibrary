package com.example.DDDLibrary.Library.Lending.Book.Domain;

import com.example.DDDLibrary.Library.Catalogue.BookType;

import java.util.UUID;

public interface Book {

    default UUID bookId() {
        return getBookInformation().getBookId();
    }

    default BookType type() {
        return getBookInformation().getBookType();
    }

    BookInformation getBookInformation();

}