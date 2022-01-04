package com.example.DDDLibrary.Library.Catalogue;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import com.example.DDDLibrary.Library.Common.Events.DomainEvent;
import lombok.Value;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.UUID;

@Value
class BookInstanceAddedToCatalogue implements DomainEvent {

    UUID eventId = UUID.randomUUID();
    @NonNull
    String isbn;
    @NonNull
    BookType type;
    @NonNull
    UUID bookId;
    Instant when = Instant.now();

    public BookInstanceAddedToCatalogue(String isbn,BookType bookType,UUID id){
        this.isbn = isbn;
        this.type = bookType;
        this.bookId = id;
    }

    BookInstanceAddedToCatalogue(BookInstance bookInstance) {
        this(bookInstance.getISBN(), bookInstance.getBookType(), bookInstance.getId());
    }

    @Override
    public UUID getAggregateId() {
        return bookId;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Instant getWhen() {
        return when;
    }

}
