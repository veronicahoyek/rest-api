package edu.ua.oop1veronicahoyek.app.exceptions;

public class EntityValidationException extends RuntimeException{
    public EntityValidationException(String message) {
        super(message);
    }
}
