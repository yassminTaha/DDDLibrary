package com.example.DDDLibrary.Library.Lending.Patron.Infrastructure;

import com.example.DDDLibrary.Library.Lending.Patron.Domain.Hold;
import com.example.DDDLibrary.Library.Lending.Patron.Domain.PatronHolds;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface HoldRespository extends CrudRepository<Hold, String> {
    int GetHoldCountByPatronId(UUID patronId);
}
