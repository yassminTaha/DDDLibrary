package com.example.DDDLibrary.Library.Lending.Book.Application;


        import com.example.DDDLibrary.Library.Catalogue.BookType;
        import com.example.DDDLibrary.Library.Common.Commands.Result;
        import com.example.DDDLibrary.Library.Common.Events.DomainEvents;
        import com.example.DDDLibrary.Library.Lending.Book.Domain.AvailableBook;
        import com.example.DDDLibrary.Library.Lending.Book.Domain.BookOnHold;
        import com.example.DDDLibrary.Library.Lending.Book.Infrastructure.AvailableBookRespository;
        import com.example.DDDLibrary.Library.Lending.LibraryBranch.Domain.LibraryBranchId;
        import com.example.DDDLibrary.Library.Lending.Patron.Domain.HoldDuration;
        import com.example.DDDLibrary.Library.Lending.Patron.Domain.Patron;
        import com.example.DDDLibrary.Library.Lending.Patron.Domain.PatronType;
        import com.example.DDDLibrary.Library.Lending.Patron.Infrastructure.HoldRespository;
        import org.junit.Assert;
        import org.junit.jupiter.api.BeforeAll;
        import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.TestInstance;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.MockitoAnnotations;
        import org.mockito.junit.jupiter.MockitoExtension;

        import javax.security.auth.Subject;
        import javax.sound.sampled.CompoundControl;
        import javax.swing.text.html.Option;
        import java.util.Optional;
        import java.util.UUID;

        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AvailableBookServiceTests {
    @InjectMocks
    private AvailableBookService subject;
    private UUID libraryBranchId;
    private UUID bookId;

    private AvailableBook availableBook;

    private Patron patron;
    private UUID patronId;

    @Mock
    private AvailableBookRespository availableBookRespository;

    @Mock
    private HoldRespository holdRespository;

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
    public void shouldFailWhenBookIsNotFound() throws Exception {
        subject = new AvailableBookService(availableBookRespository,holdRespository,domainEvents);
        patron = new Patron(patronId, PatronType.Regular);
        Exception exception = assertThrows(Exception.class, () -> {
            subject.SetBookOnHold(availableBook,patron,libraryBranchId, HoldDuration.openEnded());
        });

    }

    @Test
    public void shouldFailWhenBookIsRestrictedAndPatronRegular() throws Exception {
        given(holdRespository.GetHoldCountByPatronId(patronId)).willReturn(1);
        availableBook = new AvailableBook(bookId, BookType.Restricted,new LibraryBranchId(libraryBranchId));
        given(availableBookRespository.findById(bookId)).willReturn(Optional.of(availableBook));
        subject = new AvailableBookService(availableBookRespository,holdRespository,domainEvents);
        patron = new Patron(patronId, PatronType.Regular);
        Exception exception = assertThrows(Exception.class, () -> {
            subject.SetBookOnHold(availableBook,patron,libraryBranchId, HoldDuration.closeEnded(5));
        });
    }

    @Test
    public void shouldFailWhenOpenEndedAndPatronRegular() throws Exception {
        given(holdRespository.GetHoldCountByPatronId(patronId)).willReturn(1);
        availableBook = new AvailableBook(bookId, BookType.Restricted,new LibraryBranchId(libraryBranchId));
        given(availableBookRespository.findById(bookId)).willReturn(Optional.of(availableBook));
        subject = new AvailableBookService(availableBookRespository,holdRespository,domainEvents);
        patron = new Patron(patronId, PatronType.Regular);
        Exception exception = assertThrows(Exception.class, () -> {
            subject.SetBookOnHold(availableBook,patron,libraryBranchId, HoldDuration.openEnded());
        });
    }

    @Test
    public void shouldFailWhenExceedNumberOfOnHoldBooks() throws Exception {
        availableBook = new AvailableBook(bookId, BookType.Circulating,new LibraryBranchId(libraryBranchId));
        given(availableBookRespository.findById(bookId)).willReturn(Optional.of(availableBook));
        given(holdRespository.GetHoldCountByPatronId(patronId)).willReturn(6);
        subject = new AvailableBookService(availableBookRespository,holdRespository,domainEvents);
        patron = new Patron(patronId, PatronType.Regular);
        Exception exception = assertThrows(Exception.class, () -> {
            subject.SetBookOnHold(availableBook,patron,libraryBranchId, HoldDuration.openEnded());
        });
    }

    @Test
    public void reglarPatronWithLessThanMaxBookHoldsShouldReturnSuccess() throws Exception {
        availableBook = new AvailableBook(bookId, BookType.Circulating,new LibraryBranchId(libraryBranchId));
        given(availableBookRespository.findById(bookId)).willReturn(Optional.of(availableBook));
        given(holdRespository.GetHoldCountByPatronId(patronId)).willReturn(4);
        subject = new AvailableBookService(availableBookRespository,holdRespository,domainEvents);
        patron = new Patron(patronId, PatronType.Regular);
       Result holdResult = subject.SetBookOnHold(availableBook,patron,libraryBranchId, HoldDuration.closeEnded(5));
        Assert.assertEquals(holdResult,Result.Success);
    }

    @Test
    public void researchPatronHoldRestrictedBookShouldReturnSuccess() throws Exception {
        availableBook = new AvailableBook(bookId, BookType.Restricted,new LibraryBranchId(libraryBranchId));
        given(availableBookRespository.findById(bookId)).willReturn(Optional.of(availableBook));
        given(holdRespository.GetHoldCountByPatronId(patronId)).willReturn(4);
        subject = new AvailableBookService(availableBookRespository,holdRespository,domainEvents);
        patron = new Patron(patronId, PatronType.Researcher);
        Result holdResult = subject.SetBookOnHold(availableBook,patron,libraryBranchId, HoldDuration.closeEnded(5));
        Assert.assertEquals(holdResult,Result.Success);
    }
}
