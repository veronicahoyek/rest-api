package edu.ua.oop1veronicahoyek.app.models.DTOs.book;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookUpdateDTO {
    private String title;
    private Integer quantity;
    private String authorId;
    private Integer year;
    private String isbn;
    private String publisherId;
    private String genre;
    private Integer pageCount;
}