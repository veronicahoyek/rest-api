package edu.ua.oop1veronicahoyek.app.helper.ISBN;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

// Todo: ask Vikko about the import here, it is different from the others
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ISBN13SpecificationTest {

    private final ISBN13Specification isbn13Specification = new ISBN13Specification();

    @Test
    public void testGetPattern() {
        Pattern pattern = isbn13Specification.getPattern("9781408855652");
        assertTrue(pattern.matcher("978-1408855652").matches());
        assertTrue(pattern.matcher("978 1408855652").matches());
        assertTrue(pattern.matcher("978-1408855650").matches());
        assertTrue(pattern.matcher("9781408855652").matches());
        assertFalse(pattern.matcher("97814088556523").matches());
        assertFalse(pattern.matcher("978140885565").matches());
    }

    @Test
    public void testValidateChecksum() {
        assertTrue(isbn13Specification.validateChecksum("9781408855652"));
        assertFalse(isbn13Specification.validateChecksum("9781408855653"));
        assertFalse(isbn13Specification.validateChecksum(null));
        assertFalse(isbn13Specification.validateChecksum(""));
        assertFalse(isbn13Specification.validateChecksum("978140885565"));
        assertFalse(isbn13Specification.validateChecksum("97814088556523"));
    }
}
