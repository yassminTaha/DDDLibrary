package com.example.DDDLibrary.Library.Lending.Book.Application;

import com.example.DDDLibrary.Library.Common.Commands.Result;
import com.example.DDDLibrary.Library.Common.Events.DomainEvent;
import com.example.DDDLibrary.Library.Common.Events.DomainEvents;
import com.example.DDDLibrary.Library.Lending.Book.Domain.BookOnHold;
import com.example.DDDLibrary.Library.Lending.Book.Domain.CheckedOutBook;
import com.example.DDDLibrary.Library.Lending.Book.Infrastructure.BookOnHoldRepository;
import com.example.DDDLibrary.Library.Lending.Book.Infrastructure.CheckedOutBookRepository;
import com.example.DDDLibrary.Library.Lending.LibraryBranch.Domain.LibraryBranchId;
import com.example.DDDLibrary.Library.Lending.Patron.Application.PatronEvent;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.Hold;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.Patron;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.PatronId;
import com.example.DDDLibrary.Library.Lending.Patron.Infrastructure.HoldRespository;
import com.example.DDDLibrary.Library.Lending.Patron.Infrastructure.PatronRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public class BookOnHoldService {

    private final BookOnHoldRepository bookOnHoldRepository;
    private final PatronRepository patronRepository;
    private final DomainEvents domainEvents;
    private final CheckedOutBookRepository checkedOutBookRepository;

    public BookOnHoldService(final BookOnHoldRepository bookOnHoldRepository,
                             final PatronRepository patronRepository,
                             final DomainEvents domainEvents,
                             final CheckedOutBookRepository checkedOutBookRepository){
        this.bookOnHoldRepository = bookOnHoldRepository;
        this.patronRepository = patronRepository;
        this.domainEvents = domainEvents;
        this.checkedOutBookRepository = checkedOutBookRepository;
    }

    public Result CancelBookHold(UUID bookId, UUID patronId) throws Exception {
        Optional<BookOnHold> bookOnHold = bookOnHoldRepository.FindByBookId(bookId);
         if(bookOnHold.isEmpty())
             throw new Exception("Book is not existing");

         if(bookOnHold.get().getPatronId().getId() != patronId)
             throw new Exception("Can't cancel book held by other patron");

         bookOnHoldRepository.delete(bookOnHold.get());
         Optional<Patron> patron = patronRepository.findById(patronId);

        PatronEvent.BookHoldCanceled bookHoldCancelled = patron.get().cancelHold(bookOnHold.get());
        domainEvents.publish((DomainEvent)bookHoldCancelled);

        return  Result.Success;
    }

    public Result CheckoutBook(UUID bookId, UUID patronId) throws Exception {
        Optional<BookOnHold> bookOnHold = bookOnHoldRepository.FindByBookId(bookId);
        if(bookOnHold.isEmpty())
            throw new Exception("Book is not existing");

        if(bookOnHold.get().getPatronId().getId() != patronId)
            throw new Exception("Can't cancel book held by other patron");

        BookOnHold book = bookOnHold.get();
        CheckedOutBook checkedOutBook = new CheckedOutBook(book.bookId(),book.getBookInformation().getBookType(),book.getHoldPlacedAt(),book.getPatronId());
        checkedOutBookRepository.save(checkedOutBook);

        bookOnHoldRepository.delete(bookOnHold.get());
        Optional<Patron> patron = patronRepository.findById(patronId);

        PatronEvent.BookCheckedOut bookCheckedOut = patron.get().checkOut(bookOnHold.get());
        domainEvents.publish((DomainEvent)bookCheckedOut);

        return  Result.Success;
    }
}
