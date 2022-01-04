package com.example.DDDLibrary.Library.Catalogue;

import com.example.DDDLibrary.Library.Common.Events.DomainEvents;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CatalogServiceTests {
    @InjectMocks
    private Catalogue subject;

    @Mock
    private  BookRepository bookRepositoryRepo;

    @Mock
    private  BookInstanceRepository bookInstanceRepository;

    @Mock
    private DomainEvents domainEvents;

    @BeforeAll
    public  void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Book book = new Book("12345","Harry Potter & the philosopher's stone","J.K Rolling");
        given(bookRepositoryRepo.save(book)).willReturn(book);
        given(bookRepositoryRepo.findByISBN("12345")).willReturn(Optional.of(book));
        subject = new Catalogue(bookRepositoryRepo,bookInstanceRepository,domainEvents);
    }

    @Test
    public void shouldAddNewBook() throws Exception {
        Book book = subject.AddBook("12345","Harry Potter & the philosopher's stone","J.K Rolling");
        assertEquals("12345", book.getISBN());
        assertEquals("Harry Potter & the philosopher's stone", book.getTitle());
        assertEquals("J.K Rolling", book.getAuthor());
    }

    @Test
    public void shouldAddNewBookInstance() throws Exception {
        BookInstance bookInstance = subject.AddBookInstance("12345",BookType.Restricted);
        assertEquals("12345", bookInstance.getISBN());
        assertEquals(BookType.Restricted, bookInstance.getBookType());
    }

}
