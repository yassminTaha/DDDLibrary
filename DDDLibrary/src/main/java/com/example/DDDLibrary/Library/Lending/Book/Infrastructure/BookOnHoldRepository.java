package com.example.DDDLibrary.Library.Lending.Book.Infrastructure;

import com.example.DDDLibrary.Library.Lending.Book.Domain.BookOnHold;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookOnHoldRepository extends CrudRepository<BookOnHold, UUID> {
    public Optional<BookOnHold> FindByBookId(UUID bookId);
}
