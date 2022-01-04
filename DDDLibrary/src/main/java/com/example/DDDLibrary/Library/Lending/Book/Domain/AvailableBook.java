package com.example.DDDLibrary.Library.Lending.Book.Domain;

import com.example.DDDLibrary.Library.Catalogue.BookType;
import com.example.DDDLibrary.Library.Lending.Patron.Application.PatronEvent;
import com.example.DDDLibrary.Library.Lending.LibraryBranch.Domain.LibraryBranchId;
import lombok.NonNull;

import java.util.UUID;

public class AvailableBook implements Book {

    @NonNull
    BookInformation bookInformation;

    @NonNull
    LibraryBranchId libraryBranch;


    public AvailableBook(UUID bookId, BookType type, LibraryBranchId libraryBranchId) {
        this.bookInformation = new BookInformation(bookId,type);
        this.libraryBranch = libraryBranchId;
    }

    public boolean isRestricted() {
        return bookInformation.getBookType().equals(BookType.Restricted);
    }

    public UUID getBookId() {
        return bookInformation.getBookId();
    }


    public UUID getLibraryBranchId() {
        return libraryBranch.getId();
    }

    @Override
    public BookInformation getBookInformation() {
        return bookInformation;
    }

    public BookOnHold handle(PatronEvent.BookPlacedOnHold bookPlacedOnHold) {
        return new BookOnHold(
                bookInformation.getBookId(),
                bookInformation.getBookType(),
                new LibraryBranchId(bookPlacedOnHold.getLibraryBranchId()),
                bookPlacedOnHold.patronId());
    }
}