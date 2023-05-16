package edu.ua.oop1veronicahoyek.app.helper.ISBN;


import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ISBN10SpecificationTest {

    private final ISBN10Specification specification = new ISBN10Specification();

    @Test
    public void shouldReturnPattern() {
        Pattern pattern = specification.getPattern("1234567890");
        assertTrue(pattern.matcher("1234567890").matches());
        assertTrue(pattern.matcher("1-2-3 4 5 6 7 8-9-0").matches());
        assertFalse(pattern.matcher("123456789").matches());
        assertFalse(pattern.matcher("12345678900").matches());
    }

    @Test
    public void shouldValidateChecksum() {
        assertTrue(specification.validateChecksum("0306406152"));
        assertTrue(specification.validateChecksum("0261103571"));
        assertFalse(specification.validateChecksum("0306406153"));
        assertFalse(specification.validateChecksum("0261103572"));
        assertFalse(specification.validateChecksum(null));
        assertFalse(specification.validateChecksum(""));
        assertFalse(specification.validateChecksum("1234567890"));
    }

}
