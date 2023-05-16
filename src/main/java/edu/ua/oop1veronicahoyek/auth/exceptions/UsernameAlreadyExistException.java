package edu.ua.oop1veronicahoyek.auth.exceptions;

public class UsernameAlreadyExistException extends RuntimeException {
    public UsernameAlreadyExistException(String username) {
        super("User of username '" + username + "' already exists.");
    }
}
