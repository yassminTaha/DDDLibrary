package com.example.DDDLibrary.Library.Lending.Patron.Domain;

import com.example.DDDLibrary.Library.Lending.Book.Domain.AvailableBook;
import com.example.DDDLibrary.Library.Lending.Book.Domain.BookOnHold;
import lombok.NonNull;
import lombok.Value;

import java.util.Set;

@Value
public class PatronHolds {

    static int MAX_NUMBER_OF_HOLDS = 5;

    Set<Hold> resourcesOnHold;

    boolean a(@NonNull BookOnHold bookOnHold) {
        Hold hold = new Hold(bookOnHold.getBookId(), bookOnHold.getHoldPlacedAt(),bookOnHold.getPatronId());
        return resourcesOnHold.contains(hold);
    }

    int count() {
        return resourcesOnHold.size();
    }

    boolean maximumHoldsAfterHolding(AvailableBook book) {
        return count() + 1 == MAX_NUMBER_OF_HOLDS;
    }
}
