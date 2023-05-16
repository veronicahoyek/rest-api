package edu.ua.oop1veronicahoyek.app.isbnValidator;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ISBNValidatorTest {

    @Mock
    ConstraintValidatorContext context;

    @Test
    public void testIsValid() {
        MockitoAnnotations.openMocks(this);
        ISBNValidator validator = new ISBNValidator();

        // valid ISBN13 number
        String isbn13 = "9783161484100";
        boolean isValid = validator.isValid(isbn13, context);
        Assert.assertTrue(isValid);

        // valid ISBN10 number
        String isbn10 = "3-86680-192-0";
        isValid = validator.isValid(isbn10, context);
        Assert.assertTrue(isValid);

        // invalid ISBN number
        String invalidIsbn = "invalid";
        isValid = validator.isValid(invalidIsbn, context);
        Assert.assertFalse(isValid);
    }

    @Test
    public void testInitialize() {
        ISBNValidator validator = new ISBNValidator();
        ISBNFormatConstraint annotation = new ISBNFormatConstraint() {
            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public String message() {
                return null;
            }

            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return null;
            }
        };
        validator.initialize(annotation);
    }
}
