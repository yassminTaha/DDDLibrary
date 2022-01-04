package com.example.DDDLibrary.Library.Catalogue;

public class Book {
    String isbn;
    String title;
    Author author;

    public Book(String isbn, String title, String author)
    {
        this.isbn = isbn;
        this.title = title;
        this.author = new Author(author);

    }

    public String getISBN() {
        return this.isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author.getName();
    }
}
