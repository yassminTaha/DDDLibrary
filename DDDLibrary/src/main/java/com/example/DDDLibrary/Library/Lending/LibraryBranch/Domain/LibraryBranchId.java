package com.example.DDDLibrary.Library.Lending.LibraryBranch.Domain;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class LibraryBranchId {

    @NonNull UUID libraryBranchId;

    public LibraryBranchId(UUID libraryBranchId){
        this.libraryBranchId = libraryBranchId;
    }

    public UUID getId() {
        return  libraryBranchId;
    }
}
