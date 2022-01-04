package com.example.DDDLibrary.Library.Lending.Book.Domain;

import com.example.DDDLibrary.Library.Catalogue.BookType;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class BookInformation {

    @NonNull
    UUID bookId;

    @NonNull
    BookType bookType;

    public BookInformation(UUID bookId, BookType bookType){
        this.bookId = bookId;
        this.bookType = bookType;
    }

    public UUID getBookId() {
        return bookId;
    }

    public BookType getBookType() {
        return bookType;
    }
}