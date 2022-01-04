package com.example.DDDLibrary.Library.Lending.Patron.Domain;

import com.example.DDDLibrary.Library.Catalogue.BookType;
import com.example.DDDLibrary.Library.Lending.LibraryBranch.Domain.LibraryBranchId;
import com.example.DDDLibrary.Library.Lending.Patron.Application.PatronEvent;
import com.example.DDDLibrary.Library.Lending.Book.Domain.AvailableBook;
import com.example.DDDLibrary.Library.Lending.Book.Domain.BookOnHold;
import com.example.DDDLibrary.Library.Common.Events.EitherResult;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.NonNull;
import org.springframework.web.servlet.tags.form.OptionsTag;

import java.util.Optional;
import java.util.UUID;

import static com.example.DDDLibrary.Library.Common.Events.EitherResult.announceFailure;
import static com.example.DDDLibrary.Library.Common.Events.EitherResult.announceSuccess;
import static com.example.DDDLibrary.Library.Lending.Patron.Domain.PatronHolds.MAX_NUMBER_OF_HOLDS;
import static io.vavr.control.Either.left;

public class Patron {
    @NonNull
    private final PatronInformation patron;

    public Patron(UUID patronId,PatronType patronType){
        this.patron = new PatronInformation(new PatronId(patronId),patronType);
    }

    public UUID getPatronId(){
        return patron.getPatronId().getId();
    }


    public Either<PatronEvent.BookPlacedOnHold.BookHoldFailed, PatronEvent.BookPlacedOnHold> placeOnHold(AvailableBook book, int numberOfBooksOnHold) {
        return placeOnHold(book, HoldDuration.openEnded(),numberOfBooksOnHold);
    }

    public Either<PatronEvent.BookPlacedOnHold.BookHoldFailed, PatronEvent.BookPlacedOnHold> placeOnHold(AvailableBook aBook, HoldDuration duration, int numberOfBooksOnHold) {

        Optional<Rejection> rejection = patronCanHold(aBook, duration,numberOfBooksOnHold);
        if (rejection.isEmpty()) {
            PatronEvent.BookPlacedOnHold bookPlacedOnHold = bookPlacedOnHoldNow(aBook.getBookId(), aBook.type(), aBook.getLibraryBranchId(), patron.getPatronId(), duration);
            return announceSuccess(bookPlacedOnHold);
        }
        return announceFailure(bookHoldFailedNow(rejection.get(), aBook.getBookId(), aBook.getLibraryBranchId(), patron));
    }

    public PatronEvent.BookHoldCanceled cancelHold(BookOnHold bookOnHold){
        PatronEvent.BookHoldCanceled bookHoldCanceled = bookHoldCanceledNow(bookOnHold.bookId(),bookOnHold.getHoldPlacedAt(),bookOnHold.getPatronId());
        return bookHoldCanceled;
    }

    public PatronEvent.BookCheckedOut checkOut(BookOnHold bookOnHold){
        PatronEvent.BookCheckedOut BookCheckedOut = BookCheckedOutNow(bookOnHold.bookId(),bookOnHold.getHoldPlacedAt(),bookOnHold.getPatronId());
        return BookCheckedOut;
    }

    private PatronEvent.BookHoldFailed bookHoldFailedNow(Rejection rejection, UUID bookId, UUID libraryBranchId, PatronInformation patron) {
        return PatronEvent.BookHoldFailed.bookHoldFailedNow(rejection,bookId,new LibraryBranchId(libraryBranchId),patron);
    }

    private PatronEvent.BookPlacedOnHold bookPlacedOnHoldNow(UUID bookId, BookType type, UUID libraryBranchId, PatronId patronId, HoldDuration duration) {
        return PatronEvent.BookPlacedOnHold.now(bookId,patronId, new LibraryBranchId(libraryBranchId));
    }

    private PatronEvent.BookHoldCanceled bookHoldCanceledNow(UUID bookId, LibraryBranchId libraryBranchId, PatronId patronId) {
        return PatronEvent.BookHoldCanceled.holdCanceledNow(bookId,libraryBranchId,patronId);
    }

    private PatronEvent.BookCheckedOut BookCheckedOutNow(UUID bookId, LibraryBranchId libraryBranchId, PatronId patronId) {
        return PatronEvent.BookCheckedOut.bookCheckedOutNow(bookId,libraryBranchId,patronId);
    }

    private Optional<Rejection> patronCanHold(AvailableBook aBook, HoldDuration forDuration, int numberOfHolds) {
        Rejection rejectionWithReason;

        if (aBook.isRestricted() && patron.isRegular()) {
            rejectionWithReason = Rejection.withReason("Regular patrons cannot hold restricted books");
            return Optional.of(rejectionWithReason);
        }
        if (patron.isRegular() && numberOfHolds >= PatronHolds.MAX_NUMBER_OF_HOLDS) {
            return Optional.of(Rejection.withReason("patron cannot hold more books"));
        }
        if (patron.isRegular() && forDuration.isOpenEnded()) {
            return Optional.of(Rejection.withReason("regular patron cannot place open ended holds"));
        }

        return Optional.empty();
    }
}
