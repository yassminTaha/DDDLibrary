package com.example.DDDLibrary.Library.Lending.Patron.Domain;

import com.example.DDDLibrary.Library.Lending.LibraryBranch.Domain.LibraryBranchId;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class Hold {

    @NonNull UUID bookId;
    @NonNull LibraryBranchId libraryBranchId;
    @NonNull PatronId patronId;

    public Hold(UUID bookId, LibraryBranchId libraryBranchId,PatronId patronId){
        this.bookId = bookId;
        this.libraryBranchId = libraryBranchId;
        this.patronId = patronId;
    }
}
