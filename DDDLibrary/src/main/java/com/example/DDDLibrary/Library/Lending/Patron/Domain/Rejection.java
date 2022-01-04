package com.example.DDDLibrary.Library.Lending.Patron.Domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class Rejection {

    @Value
    static class Reason {
        @NonNull
        String reason;

        public Reason(String reason){
            this.reason = reason;
        }

        public String getReason(){
            return this.reason;
        }
    }

    @NonNull
    Reason reason;

    public Rejection(Reason reason){
        this.reason = reason;
    }

    public String getReason(){
        return  this.reason.getReason();
    }

    static Rejection withReason(String reason) {
        return new Rejection(new Reason(reason));
    }
}