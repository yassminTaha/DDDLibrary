package com.example.DDDLibrary.Library.Catalogue;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, String> {
    Optional<Book> findByISBN(String isbn);
}