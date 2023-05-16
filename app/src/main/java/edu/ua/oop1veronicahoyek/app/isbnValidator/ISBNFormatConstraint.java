package edu.ua.oop1veronicahoyek.app.isbnValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ISBNValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ISBNFormatConstraint {
    String message() default "Invalid ISBN";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
