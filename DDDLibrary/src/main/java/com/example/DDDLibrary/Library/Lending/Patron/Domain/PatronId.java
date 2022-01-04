package com.example.DDDLibrary.Library.Lending.Patron.Domain;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class PatronId {
    @NonNull UUID patronId;

    public PatronId(UUID patronId){
        this.patronId = patronId;
    }

    public UUID getId() {
        return  patronId;
    }
}
