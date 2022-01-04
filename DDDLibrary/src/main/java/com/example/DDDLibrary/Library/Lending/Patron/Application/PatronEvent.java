package com.example.DDDLibrary.Library.Lending.Patron.Application;

import com.example.DDDLibrary.Library.Common.Events.DomainEvent;
import com.example.DDDLibrary.Library.Common.Events.DomainEvents;
import com.example.DDDLibrary.Library.Lending.LibraryBranch.Domain.LibraryBranchId;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.PatronId;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.PatronInformation;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.Rejection;
import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

public interface PatronEvent extends DomainEvent {

    default PatronId patronId() {
        return new PatronId(getPatronId());
    }
    UUID getPatronId();
    default UUID getAggregateId() {
        return getPatronId();
    }
    default List<DomainEvent> normalize() {
        return List.of(this);
    }




    @Value
    class BookHoldCanceled implements PatronEvent {
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull Instant when;
        @NonNull UUID patronId;
        @NonNull UUID bookId;
        @NonNull UUID libraryBranchId;

        public BookHoldCanceled(Instant when,UUID patronId,UUID bookId,UUID libraryBranchId){
            this.when = when;
            this.patronId = patronId;
            this.bookId = bookId;
            this.libraryBranchId = libraryBranchId;
        }

        public static BookHoldCanceled holdCanceledNow(UUID bookId, LibraryBranchId libraryBranchId, PatronId patronId) {
            return new BookHoldCanceled(
                    Instant.now(),
                    patronId.getId(),
                    bookId,
                    libraryBranchId.getId());
        }


        @Override
        public UUID getPatronId() {
            return patronId;
        }

        public UUID getLibraryBranchId(){
            return libraryBranchId;
        }

        @Override
        public UUID getEventId() {
            return this.eventId;
        }

        @Override
        public Instant getWhen() {
            return this.when;
        }
    }

    @Value
    class BookHoldExpired implements PatronEvent {
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull Instant when;
        @NonNull UUID patronId;
        @NonNull UUID bookId;
        @NonNull UUID libraryBranchId;
        public BookHoldExpired(Instant when,UUID patronId,UUID bookId,UUID libraryBranchId){
            this.when = when;
            this.patronId = patronId;
            this.bookId = bookId;
            this.libraryBranchId = libraryBranchId;
        }
        public static BookHoldExpired now(UUID bookId, PatronId patronId, LibraryBranchId libraryBranchId) {
            return new BookHoldExpired(
                    Instant.now(),
                    patronId.getId(),
                    bookId,
                    libraryBranchId.getId());
        }

        @Override
        public UUID getPatronId() {
            return patronId;
        }

        public UUID getLibraryBranchId(){
            return libraryBranchId;
        }

        @Override
        public UUID getEventId() {
            return this.eventId;
        }

        @Override
        public Instant getWhen() {
            return this.when;
        }
    }

    @Value
    class BookReturned implements PatronEvent {
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull Instant when;
        @NonNull UUID patronId;
        @NonNull UUID bookId;
        @NonNull UUID libraryBranchId;
        public BookReturned(Instant when,UUID patronId,UUID bookId,UUID libraryBranchId){
            this.when = when;
            this.patronId = patronId;
            this.bookId = bookId;
            this.libraryBranchId = libraryBranchId;
        }
        public static BookReturned now(UUID bookId, PatronId patronId, LibraryBranchId libraryBranchId) {
            return new BookReturned(
                    Instant.now(),
                    patronId.getId(),
                    bookId,
                    libraryBranchId.getId());
        }

        @Override
        public UUID getPatronId() {
            return patronId;
        }

        public UUID getLibraryBranchId(){
            return libraryBranchId;
        }

        @Override
        public UUID getEventId() {
            return this.eventId;
        }

        @Override
        public Instant getWhen() {
            return this.when;
        }
    }

    @Value
    class BookPlacedOnHold implements PatronEvent {
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull UUID patronId;
        @NonNull UUID bookId;
        @NonNull UUID libraryBranchId;
        @NonNull Instant to;
        @NonNull Instant when;

        public BookPlacedOnHold(UUID patronId,UUID bookId,UUID libraryBranchId,Instant to){
            this.patronId = patronId;
            this.bookId = bookId;
            this.libraryBranchId = libraryBranchId;
            this.to = to;
            this.when = Instant.now();
        }
        public static BookPlacedOnHold now(UUID bookId, PatronId patronId, LibraryBranchId libraryBranchId) {
            return new BookPlacedOnHold(
                    patronId.getId(),
                    bookId,
                    libraryBranchId.getId(),Instant.now());
        }

        @Override
        public UUID getPatronId() {
            return patronId;
        }

        public UUID getLibraryBranchId(){
            return libraryBranchId;
        }

        public Instant getHoldTill(){
                return to;
        }

        @Override
        public UUID getEventId() {
            return this.eventId;
        }

        public Instant getWhen(){
            return when;
        }

    }

    @Value
    class BookHoldFailed implements PatronEvent {
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull String reason;
        @NonNull Instant when;
        @NonNull UUID patronId;
        @NonNull UUID bookId;
        @NonNull UUID libraryBranchId;

        public BookHoldFailed(String reason,Instant when, UUID patronId,UUID bookId,UUID libraryBranchId){
            this.reason = reason;
            this.when = when;
            this.patronId = patronId;
            this.bookId = bookId;
            this.libraryBranchId = libraryBranchId;
        }

        public static BookHoldFailed bookHoldFailedNow(Rejection rejection, UUID bookId, LibraryBranchId libraryBranchId, PatronInformation patronInformation) {
            return new BookHoldFailed(
                    rejection.getReason(),
                    Instant.now(),
                    patronInformation.getPatronId().getId(),
                    bookId,
                    libraryBranchId.getId());
        }

        @Override
        public UUID getPatronId() {
            return patronId;
        }


        @Override
        public UUID getEventId() {
            return this.eventId;
        }

        @Override
        public Instant getWhen() {
            return this.when;
        }
    }


    @Value
    class BookCheckedOut implements PatronEvent {
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull Instant when;
        @NonNull UUID patronId;
        @NonNull UUID bookId;
        @NonNull UUID libraryBranchId;

        public BookCheckedOut(Instant when,UUID patronId,UUID bookId,UUID libraryBranchId){
            this.when = when;
            this.patronId = patronId;
            this.bookId = bookId;
            this.libraryBranchId = libraryBranchId;
        }

        public static BookCheckedOut bookCheckedOutNow(UUID bookId, LibraryBranchId libraryBranchId, PatronId patronId) {
            return new BookCheckedOut(
                    Instant.now(),
                    patronId.getId(),
                    bookId,
                    libraryBranchId.getId());
        }


        @Override
        public UUID getPatronId() {
            return patronId;
        }

        public UUID getLibraryBranchId(){
            return libraryBranchId;
        }

        @Override
        public UUID getEventId() {
            return this.eventId;
        }

        @Override
        public Instant getWhen() {
            return this.when;
        }
    }


}
