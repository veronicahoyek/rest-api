package edu.ua.oop1veronicahoyek.app.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.ua.oop1veronicahoyek.app.isbnValidator.ISBNFormatConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Arrays;
import java.util.List;

@Document("books")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private String id;

    @Size(min = 1, message = "Title too short")
    @Size(max = 100, message = "Title too long")
    private String title;

    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @Min(value = 1700, message = "Accepted book are written after the year 1700 AC")
    private Integer year;

    @ISBNFormatConstraint
    private String isbn;

    @JsonBackReference
    @DocumentReference
    private Author author;

    @JsonBackReference
    @DocumentReference
    private Publisher publisher;

    @Pattern(regexp = "^[a-zA-Z-]+$", message = "Genre should be one word and characters only")
    private String genre;

    @Min(value = 1, message = "Page Count must be greater than 0")
    private Integer pageCount;

    static public List<String> getListColumns() {
        return Arrays.asList("title", "author", "year", "isbn", "genre", "pageCount");
    }
}