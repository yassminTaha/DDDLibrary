package com.example.DDDLibrary.Library.Lending.Book.Domain;

import com.example.DDDLibrary.Library.Catalogue.BookType;
import com.example.DDDLibrary.Library.Lending.Patron.Application.PatronEvent;
import com.example.DDDLibrary.Library.Lending.LibraryBranch.Domain.LibraryBranchId;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.PatronId;
import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

public class BookOnHold implements Book {
    @NonNull
    BookInformation bookInformation;

    @NonNull
    LibraryBranchId holdPlacedAt;

    @NonNull
    PatronId byPatron;

    @NonNull
    Instant holdTill;


    public BookOnHold(UUID bookId, BookType bookType, LibraryBranchId holdPlacedAt,PatronId byPatron){
        this.bookInformation = new BookInformation(bookId,bookType);
        this.holdPlacedAt = holdPlacedAt;
        this.byPatron = byPatron;
    }

    public AvailableBook handle(PatronEvent.BookReturned bookReturned) {
        return new AvailableBook(
                bookInformation.getBookId(),bookInformation.getBookType(), new LibraryBranchId(bookReturned.getLibraryBranchId()));
    }

    public AvailableBook handle(PatronEvent.BookHoldExpired bookHoldExpired) {
        return new AvailableBook(
                bookInformation.getBookId(),bookInformation.getBookType(), new LibraryBranchId(bookHoldExpired.getLibraryBranchId()));
    }


    public AvailableBook handle(PatronEvent.BookHoldCanceled bookHoldCanceled) {
        return new AvailableBook(
                bookInformation.getBookId(),bookInformation.getBookType(), new LibraryBranchId(bookHoldCanceled.getLibraryBranchId()));
    }


    public UUID getBookId(){
        return this.bookInformation.getBookId();
    }

    public PatronId getPatronId(){
        return this.byPatron;
    }

    public LibraryBranchId getHoldPlacedAt(){
        return this.holdPlacedAt;
    }

    @Override
    public BookInformation getBookInformation() {
        return bookInformation;
    }
}
