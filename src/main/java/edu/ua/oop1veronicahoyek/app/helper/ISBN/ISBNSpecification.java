package edu.ua.oop1veronicahoyek.app.helper.ISBN;

import java.util.regex.Pattern;

public interface ISBNSpecification {
    default boolean isISBNType(String isbn) {
        return getPattern(isbn).matcher(isbn).matches();
    }
    boolean validateChecksum(String isbn);
    Pattern getPattern(String isbn);
}
