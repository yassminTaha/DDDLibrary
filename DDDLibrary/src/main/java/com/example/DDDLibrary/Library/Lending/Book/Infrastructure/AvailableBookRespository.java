package com.example.DDDLibrary.Library.Lending.Book.Infrastructure;

import com.example.DDDLibrary.Library.Lending.Book.Domain.AvailableBook;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AvailableBookRespository extends CrudRepository<AvailableBook, UUID> {
}
