package edu.ua.oop1veronicahoyek.app.helper.ISBN;

import edu.ua.oop1veronicahoyek.app.helper.CharUtils;

import java.util.regex.Pattern;

public class ISBN10Specification implements ISBNSpecification {
    static private final String pattern = "^(?:\\d[ |-]?){10}$";
    @Override
    public Pattern getPattern(String isbn) {
        return Pattern.compile(pattern);
    }
    @Override
    public boolean validateChecksum(String isbn) {
        if (isbn == null) {
            return false;
        }

        isbn = isbn.replaceAll("-", "").replaceAll(" ", "");
        if (isbn.length() != 10) {
            return false;
        }

        int sum = 0;
        for (int i=0; i<10; i++) {
            sum += CharUtils.charToDigit(isbn.charAt(i)) * (10 - i);
        }

        int computedChecksum = sum % 11;
        return computedChecksum == 0;
    }
}
