package edu.ua.oop1veronicahoyek.app.exceptions;

public class ColumnNotFoundException extends RuntimeException{
    public ColumnNotFoundException(String columnName, String className) {
        super("Column '" + columnName + "' not found in class '" + className + "'.");
    }
}
