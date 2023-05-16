package edu.ua.oop1veronicahoyek.app.models.DTOs.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@AllArgsConstructor
public class BookInsertDTO {
    @NonNull
    final private String title;
    @Builder.Default private Integer quantity = 0;
    @NonNull final private String authorId;
    @NonNull final private Integer year;
    @NonNull final private String isbn;
    @NonNull final private String publisherId;
    @NonNull final private String genre;
    @NonNull final private Integer pageCount;
}
