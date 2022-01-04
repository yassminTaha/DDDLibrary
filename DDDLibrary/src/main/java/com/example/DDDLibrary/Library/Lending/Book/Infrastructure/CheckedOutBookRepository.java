package com.example.DDDLibrary.Library.Lending.Book.Infrastructure;

import com.example.DDDLibrary.Library.Lending.Book.Domain.CheckedOutBook;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CheckedOutBookRepository extends CrudRepository<CheckedOutBook, UUID> {
}
