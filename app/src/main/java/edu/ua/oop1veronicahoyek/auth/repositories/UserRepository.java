package edu.ua.oop1veronicahoyek.auth.repositories;

import edu.ua.oop1veronicahoyek.auth.models.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
