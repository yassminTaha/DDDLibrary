package com.example.DDDLibrary.Library.Lending.Patron.Infrastructure;

import com.example.DDDLibrary.Library.Lending.Patron.Domain.Patron;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PatronRepository extends CrudRepository<Patron, UUID> {
}
