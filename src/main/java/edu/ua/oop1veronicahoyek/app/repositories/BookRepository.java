package edu.ua.oop1veronicahoyek.app.repositories;

import edu.ua.oop1veronicahoyek.app.models.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    Page<Book> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    Book findByTitleAndYear(String title, Integer year);
    List<Book> findByYearBetween(int startYear, int endYear);
}
