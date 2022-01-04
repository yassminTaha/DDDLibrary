package com.example.DDDLibrary.Library.Lending.Patron.Domain;

import lombok.Value;

@Value
public class NumberOfDays {

    int days;

    private NumberOfDays(int days) {
        if(days <= 0) {
            throw new IllegalArgumentException("Cannot use negative integer or zero as number of days");
        }
        this.days = days;
    }

    public static NumberOfDays of(int days) {
        return new NumberOfDays(days);
    }

    boolean isGreaterThan(int days) {
        return this.days > days;
    }

    public int getDays(){
        return this.days;
    }
}