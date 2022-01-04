package com.example.DDDLibrary.Library.Catalogue;

import java.util.Optional;
import com.example.DDDLibrary.Library.Common.Events.DomainEvents;

public class Catalogue {
    private final BookRepository bookRepository;
    private final BookInstanceRepository bookInstanceRepository;
    private final DomainEvents domainEvents;

    public Catalogue(final BookRepository bookRepositoryRepo, final BookInstanceRepository bookInstanceRepository,final DomainEvents domainEvents) {
        this.bookRepository = bookRepositoryRepo;
        this.bookInstanceRepository = bookInstanceRepository;
        this.domainEvents = domainEvents;
    }

    public Book AddBook(String isbn,String title, String author) {
        Book book = new Book(isbn,title,author);
       return this.bookRepository.save(book);
    }

    public BookInstance AddBookInstance(String isbn, BookType bookType) {
        Optional<Book> book = bookRepository.findByISBN(isbn);
       BookInstance bookInstance = new BookInstance(book.get(),bookType);
       return saveAndPublishEvent(bookInstance);
    }

    private BookInstance saveAndPublishEvent(BookInstance bookInstance) {
        bookInstanceRepository.save(bookInstance);
        domainEvents.publish(new BookInstanceAddedToCatalogue(bookInstance));
        return bookInstance;
    }
}
