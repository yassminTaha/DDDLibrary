package com.example.DDDLibrary.Library.Lending.Patron.Domain;

import lombok.NonNull;
import lombok.Value;

import static com.example.DDDLibrary.Library.Lending.Patron.Domain.PatronType.Regular;

@Value
public class PatronInformation {

    @NonNull PatronId patronId;

    @NonNull PatronType type;

    public PatronInformation(PatronId patronId, PatronType patronType){
        this.patronId = patronId;
        this.type = patronType;
    }

    boolean isRegular() {
        return type.equals(Regular);
    }

    public PatronId getPatronId(){
        return this.patronId;
    }
}
