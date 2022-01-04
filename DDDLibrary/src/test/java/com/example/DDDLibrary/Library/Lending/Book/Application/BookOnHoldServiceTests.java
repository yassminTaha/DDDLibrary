package com.example.DDDLibrary.Library.Lending.Book.Application;

import com.example.DDDLibrary.Library.Catalogue.BookType;
import com.example.DDDLibrary.Library.Common.Commands.Result;
import com.example.DDDLibrary.Library.Common.Events.DomainEvents;
import com.example.DDDLibrary.Library.Lending.Book.Domain.AvailableBook;
import com.example.DDDLibrary.Library.Lending.Book.Domain.BookOnHold;
import com.example.DDDLibrary.Library.Lending.Book.Infrastructure.BookOnHoldRepository;
import com.example.DDDLibrary.Library.Lending.Book.Infrastructure.CheckedOutBookRepository;
import com.example.DDDLibrary.Library.Lending.LibraryBranch.Domain.LibraryBranchId;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.HoldDuration;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.Patron;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.PatronId;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.PatronType;
import com.example.DDDLibrary.Library.Lending.Patron.Infrastructure.PatronRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookOnHoldServiceTests {
    @InjectMocks
    private BookOnHoldService subject;
    private UUID libraryBranchId;
    private UUID bookId;

    private BookOnHold bookOnHold;

    private Patron patron;
    private UUID patronId;

    @Mock
    private BookOnHoldRepository bookOnHoldRepository;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private CheckedOutBookRepository checkedOutBookRepository;

    @Mock
    private DomainEvents domainEvents;

    @BeforeAll
    public  void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        libraryBranchId = UUID.randomUUID();
        bookId = UUID.randomUUID();
        patronId = UUID.randomUUID();
    }

    @Test
    public void shouldFailWhenCancelHoldOfNotExisisting() throws Exception {
        subject = new BookOnHoldService(bookOnHoldRepository,patronRepository,domainEvents,checkedOutBookRepository);
        patron = new Patron(patronId, PatronType.Regular);
        Exception exception = assertThrows(Exception.class, () -> {
            subject.CancelBookHold(bookId,patronId);
        });
    }

    @Test
    public void shouldFailWhenPatronCancelOtherPatronHold() throws Exception {
        UUID patronWithHoldId = UUID.randomUUID();
       BookOnHold bookOnHold = new BookOnHold(bookId, BookType.Circulating,new LibraryBranchId(libraryBranchId),new PatronId(patronWithHoldId));
        given(bookOnHoldRepository.FindByBookId(bookId)).willReturn(Optional.of(bookOnHold));
        subject = new BookOnHoldService(bookOnHoldRepository,patronRepository,domainEvents,checkedOutBookRepository);
        Exception exception = assertThrows(Exception.class, () -> {
            subject.CancelBookHold(bookId,patronId);
        });
    }

    @Test
    public void shouldSucceedWhenPatronCancelsHisHold() throws Exception {
        BookOnHold bookOnHold = new BookOnHold(bookId, BookType.Circulating,new LibraryBranchId(libraryBranchId),new PatronId(patronId));
        patron = new Patron(patronId,PatronType.Regular);
        given(bookOnHoldRepository.FindByBookId(bookId)).willReturn(Optional.of(bookOnHold));
        given(patronRepository.findById(patronId)).willReturn(Optional.of(patron));
        subject = new BookOnHoldService(bookOnHoldRepository,patronRepository,domainEvents,checkedOutBookRepository);
        Result cancelResult = subject.CancelBookHold(bookId,patronId);
        Assert.assertEquals(cancelResult,Result.Success);
    }

    @Test
    public void shouldFailWhenCheckoutHoldOfNotExisisting() throws Exception {
        subject = new BookOnHoldService(bookOnHoldRepository,patronRepository,domainEvents,checkedOutBookRepository);
        patron = new Patron(patronId, PatronType.Regular);
        Exception exception = assertThrows(Exception.class, () -> {
            subject.CheckoutBook(bookId,patronId);
        });
    }

    @Test
    public void shouldFailWhenPatronCheckoutOtherPatronHold() throws Exception {
        UUID patronWithHoldId = UUID.randomUUID();
        BookOnHold bookOnHold = new BookOnHold(bookId, BookType.Circulating,new LibraryBranchId(libraryBranchId),new PatronId(patronWithHoldId));
        given(bookOnHoldRepository.FindByBookId(bookId)).willReturn(Optional.of(bookOnHold));
        subject = new BookOnHoldService(bookOnHoldRepository,patronRepository,domainEvents,checkedOutBookRepository);
        Exception exception = assertThrows(Exception.class, () -> {
            subject.CheckoutBook(bookId,patronId);
        });
    }

    @Test
    public void shouldSucceedWhenPatronCheckoutsHisHold() throws Exception {
        BookOnHold bookOnHold = new BookOnHold(bookId, BookType.Circulating,new LibraryBranchId(libraryBranchId),new PatronId(patronId));
        patron = new Patron(patronId,PatronType.Regular);
        given(bookOnHoldRepository.FindByBookId(bookId)).willReturn(Optional.of(bookOnHold));
        given(patronRepository.findById(patronId)).willReturn(Optional.of(patron));
        subject = new BookOnHoldService(bookOnHoldRepository,patronRepository,domainEvents,checkedOutBookRepository);
        Result cancelResult = subject.CancelBookHold(bookId,patronId);
        Assert.assertEquals(cancelResult,Result.Success);
    }



}
