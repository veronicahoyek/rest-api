package edu.ua.oop1veronicahoyek.app.models.DTOs.book;

import edu.ua.oop1veronicahoyek.app.models.entities.Book;
import lombok.Data;

@Data
public class BookPrintDTO {
    private String id;
    private String title;
    private Integer quantity;
    private Integer year;
    private String isbn;
    private String authorName;
    private String publisherName;
    private String genre;
    private Integer pageCount;

    public BookPrintDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.quantity = book.getQuantity();
        this.year = book.getYear();
        this.isbn = book.getIsbn();
        this.authorName = book.getAuthor().getName();
        this.publisherName = book.getPublisher().getName();
        this.genre = book.getGenre();
        this.pageCount = book.getPageCount();
    }
}
