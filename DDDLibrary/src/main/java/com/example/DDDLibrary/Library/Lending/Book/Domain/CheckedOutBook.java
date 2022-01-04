package com.example.DDDLibrary.Library.Lending.Book.Domain;

import com.example.DDDLibrary.Library.Catalogue.BookType;
import com.example.DDDLibrary.Library.Lending.LibraryBranch.Domain.LibraryBranchId;
import com.example.DDDLibrary.Library.Lending.Patron.Application.PatronEvent;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.PatronId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@EqualsAndHashCode(of = "bookInformation")
public class CheckedOutBook implements Book {

    @NonNull
    BookInformation bookInformation;

    @NonNull
    LibraryBranchId checkedOutAt;

    @NonNull
    PatronId byPatron;


    public CheckedOutBook(UUID bookId, BookType type, LibraryBranchId libraryBranchId, PatronId patronId) {
        this.bookInformation = new BookInformation(bookId,type);
        this.checkedOutAt = libraryBranchId;
        this.byPatron = patronId;
    }

    public UUID getBookId() {
        return bookInformation.getBookId();
    }


    @Override
    public BookInformation getBookInformation() {
        return this.bookInformation;
    }
}

