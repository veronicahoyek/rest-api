package edu.ua.oop1veronicahoyek.app.helper.ISBN;

import edu.ua.oop1veronicahoyek.app.helper.CharUtils;

import java.util.regex.Pattern;

public class ISBN13Specification implements ISBNSpecification {
    private final String pattern = "^(?:\\d[ |-]?){13}$";
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
        if (isbn.length() != 13) {
            return false;
        }

        int sum = CharUtils.charToDigit(isbn.charAt(0));
        for (int i=1; i<13; i+=2) {
            sum += CharUtils.charToDigit(isbn.charAt(i)) * 3;
            sum += CharUtils.charToDigit(isbn.charAt(i + 1));
        }

        int computedChecksum = sum % 10;
        return computedChecksum == 0;
    }
}
