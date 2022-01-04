package com.example.DDDLibrary.Library.Catalogue;

import java.util.UUID;

public class BookInstance {
    String ISBN;
    UUID BookId;
    BookType BookType;
    public BookInstance(Book book, BookType bookType) {
        this.BookId = UUID.randomUUID();
        this.ISBN = book.getISBN();
        this.BookType = bookType;
    }

    public  String getISBN(){
        return this.ISBN;
    }

    public  UUID getId(){
        return this.BookId;
    }

    public  BookType getBookType(){
        return this.BookType;
    }
}
