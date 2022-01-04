package com.example.DDDLibrary.Library.Lending.Book.Application;

import com.example.DDDLibrary.Library.Common.Commands.Result;
import com.example.DDDLibrary.Library.Common.Events.DomainEvent;
import com.example.DDDLibrary.Library.Common.Events.DomainEvents;
import com.example.DDDLibrary.Library.Lending.Book.Domain.AvailableBook;
import com.example.DDDLibrary.Library.Lending.Book.Domain.BookOnHold;
import com.example.DDDLibrary.Library.Lending.Book.Infrastructure.AvailableBookRespository;
import com.example.DDDLibrary.Library.Lending.Patron.Application.PatronEvent;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.HoldDuration;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.Patron;
import com.example.DDDLibrary.Library.Lending.Patron.Infrastructure.HoldRespository;
import io.vavr.control.Either;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class AvailableBookService {

    private final AvailableBookRespository availableBookRepository;
    private final HoldRespository holdRespository;
    private final DomainEvents domainEvents;

    public AvailableBookService (final AvailableBookRespository availableBookRepository,  final HoldRespository holdRepository, final DomainEvents domainEvents){
        this.availableBookRepository = availableBookRepository;
        this.holdRespository = holdRepository;
        this.domainEvents = domainEvents;
    }
    public Result SetBookOnHold(AvailableBook book, Patron patron, UUID libraryBranchId, HoldDuration holdDuration) throws Exception {
         Optional<AvailableBook> availableBook = availableBookRepository.findById(book.getBookId());
         if(availableBook.isEmpty())
             throw  new Exception("Book not found");
        PatronEvent bookPlacedOnHold = new PatronEvent.BookPlacedOnHold(patron.getPatronId(),availableBook.get().getBookId(),libraryBranchId, Instant.now().plusSeconds(1000));
        int numberOfBooksHeldByPatron = holdRespository.GetHoldCountByPatronId(patron.getPatronId());
        Either<PatronEvent.BookHoldFailed, PatronEvent.BookPlacedOnHold> bookHoldResult = patron.placeOnHold(availableBook.get(),holdDuration,numberOfBooksHeldByPatron);
        if(bookHoldResult.isLeft())
            throw  new Exception("Book hold failed");
        domainEvents.publish((DomainEvent)bookHoldResult.get());
        return Result.Success;
    }



}