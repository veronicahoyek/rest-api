package edu.ua.oop1veronicahoyek.app.isbnValidator;

import edu.ua.oop1veronicahoyek.app.helper.ISBN.ISBN10Specification;
import edu.ua.oop1veronicahoyek.app.helper.ISBN.ISBN13Specification;
import edu.ua.oop1veronicahoyek.app.helper.ISBN.ISBNSpecification;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ISBNFormatConstraint, String> {

    @Override
    public void initialize(ISBNFormatConstraint isbnFormat) {
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        ISBNSpecification isbn13Spec = new ISBN13Specification();
        ISBNSpecification isbn10Spec = new ISBN10Specification();

        ISBNSpecification isbnSpec;
        if (isbn13Spec.isISBNType(isbn)) {
            isbnSpec = isbn13Spec;
        } else if (isbn10Spec.isISBNType(isbn)) {
            isbnSpec = isbn10Spec;
        } else return false;

        return isbnSpec.validateChecksum(isbn);
    }
}
