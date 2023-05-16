package edu.ua.oop1veronicahoyek.app.repositories;

import edu.ua.oop1veronicahoyek.app.models.entities.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Author findByNameAndYearOfBirth(String name, Integer year);
    Optional<Author> findByName(String name);
}
