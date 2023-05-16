package edu.ua.oop1veronicahoyek.app.repositories;

import edu.ua.oop1veronicahoyek.app.models.entities.Publisher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PublisherRepository extends MongoRepository<Publisher, String> {
    Publisher findByNameAndFoundationYear(String name, Integer year);
    Optional<Publisher> findByName(String name);
}
