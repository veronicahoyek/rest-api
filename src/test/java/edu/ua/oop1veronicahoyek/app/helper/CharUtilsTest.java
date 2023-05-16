package edu.ua.oop1veronicahoyek.app.helper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharUtilsTest {

    @Test
    public void charToDigitTest() {
        char c1 = '0';
        char c2 = '5';
        char c3 = '9';
        char c4 = 'a';

        int i1 = CharUtils.charToDigit(c1);
        int i2 = CharUtils.charToDigit(c2);
        int i3 = CharUtils.charToDigit(c3);
        int i4 = CharUtils.charToDigit(c4);


        assertEquals(0, i1);
        assertEquals(5, i2);
        assertEquals(9, i3);
        assertEquals(-1, i4);
    }
}
